package com.fiap.postech.pedidohub.pedido.service;

import com.fiap.postech.pedidohub.pedido.api.dto.*;
import com.fiap.postech.pedidohub.pedido.domain.model.Pedido;
import com.fiap.postech.pedidohub.pedido.domain.exceptions.InvalidPedidoException;
import com.fiap.postech.pedidohub.pedido.domain.exceptions.PedidoProdutoNotFoundException;
import com.fiap.postech.pedidohub.pedido.domain.model.PedidoItem;
import com.fiap.postech.pedidohub.pedido.gateway.port.PedidoRepositoryPort;
import com.fiap.postech.pedidohub.pedido.gateway.port.PedidoServicePort;
import com.fiap.postech.pedidohub.pedido.gateway.client.PedidoClienteClient;
import com.fiap.postech.pedidohub.pedido.gateway.client.PedidoProdutoClient;
import com.fiap.postech.pedidohub.commom.utils.ConstantUtils;
import com.fiap.postech.pedidohub.commom.utils.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PedidoServiceImpl implements PedidoServicePort {

    @Autowired
    private PedidoClienteClient pedidoClienteClient;

    @Autowired
    private PedidoProdutoClient produtoClient;

    @Autowired
    private PedidoRepositoryPort repositoryPort;

    @Override
    public ResponseDto cadastrarPedido(PedidoRequest request) {
        // Busca cliente
        PedidoClienteDto cliente = pedidoClienteClient.buscarPorCpf(request.getCpfCliente());
        Integer idCliente = cliente.getIdCliente();

        // Para cada item na request, busca o produto e monta PedidoItem completo
        List<PedidoItem> itens = new ArrayList<>();
        for (PedidoItemRequest itemReq : request.getItens()) {
            try {
                PedidoProdutoDto produto = produtoClient.buscarPorSku(itemReq.getSkuProduto());
                PedidoItem item = new PedidoItem(
                        null,
                        produto.getIdProduto(),
                        itemReq.getQuantidadeItem(),
                        produto.getPrecoProduto()
                );
                itens.add(item);
            } catch (feign.FeignException.NotFound e) {
                log.warn("Produto não encontrado para o SKU: {}", itemReq.getSkuProduto());
                throw new PedidoProdutoNotFoundException(ConstantUtils.PRODUTO_NAO_ENCONTRADO);
            }
        }

        Pedido pedido = new Pedido(
                null,
                idCliente,
                PedidoStatus.ABERTO,
                LocalDateTime.now(),
                null,
                itens
        );

        // Calcula valor total
        BigDecimal valorTotal = itens.stream()
                .map(i -> i.getPrecoUnitarioItem().multiply(BigDecimal.valueOf(i.getQuantidadeItem())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        pedido.setValorTotalPedido(valorTotal);

        validarPedido(pedido);

        return repositoryPort.cadastrarPedido(pedido);
    }

    private void validarPedido(Pedido pedido) {
        if (pedido == null) {
            throw new InvalidPedidoException(ConstantUtils.PEDIDO_NAO_PODE_SER_NULO);
        }
        if (!pedido.clienteValido()) {
            throw new InvalidPedidoException(ConstantUtils.CLIENTE_NAO_ENCONTRADO);
        }
        if (!pedido.itensValidos()) {
            throw new InvalidPedidoException(ConstantUtils.ITENS_PEDIDO_INVALIDOS);
        }
        if (!pedido.valorTotalValido()) {
            throw new InvalidPedidoException(ConstantUtils.VALOR_TOTAL_PEDIDO_INVALIDO);
        }
    }

}
