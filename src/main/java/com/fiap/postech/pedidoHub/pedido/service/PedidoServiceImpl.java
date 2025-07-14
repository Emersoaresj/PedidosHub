package com.fiap.postech.pedidohub.pedido.service;

import com.fiap.postech.pedidohub.commom.config.ErroInternoException;
import com.fiap.postech.pedidohub.pedido.api.dto.*;
import com.fiap.postech.pedidohub.pedido.api.dto.client.cliente.PedidoClienteDto;
import com.fiap.postech.pedidohub.pedido.api.dto.client.produto.PedidoProdutoDto;
import com.fiap.postech.pedidohub.pedido.api.dto.kafka.PedidoItemKafkaDTO;
import com.fiap.postech.pedidohub.pedido.api.dto.kafka.PedidoKafkaDTO;
import com.fiap.postech.pedidohub.pedido.domain.exceptions.PedidoNotFoundException;
import com.fiap.postech.pedidohub.pedido.domain.model.Pedido;
import com.fiap.postech.pedidohub.pedido.domain.exceptions.InvalidPedidoException;
import com.fiap.postech.pedidohub.pedido.domain.exceptions.PedidoProdutoNotFoundException;
import com.fiap.postech.pedidohub.pedido.domain.model.PedidoItem;
import com.fiap.postech.pedidohub.pedido.gateway.kafka.port.PedidoProducerPort;
import com.fiap.postech.pedidohub.pedido.gateway.port.PedidoItemRepositoryPort;
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
import java.util.Map;

@Slf4j
@Service
public class PedidoServiceImpl implements PedidoServicePort {

    @Autowired
    private PedidoClienteClient pedidoClienteClient;

    @Autowired
    private PedidoProdutoClient produtoClient;

    @Autowired
    private PedidoRepositoryPort repositoryPort;

    @Autowired
    private PedidoProducerPort pedidoProducerPort;

    @Autowired
    private PedidoItemRepositoryPort pedidoItemRepositoryPort;

    @Override
    public ResponseDto cadastrarPedido(PedidoRequest request) {

        Integer idCliente = buscarIdCliente(request.getCpfCliente());

        List<PedidoItem> itens = montarItensPedido(request.getItens());

        Pedido pedido = montarPedido(idCliente, itens);

        validarPedido(pedido);

        ResponseDto response = repositoryPort.cadastrarPedido(pedido);
        Integer idPedido = buscaIdPedido(response);
        pedido.setIdPedido(idPedido);

        // Envio do pedido para o Kafka
        PedidoKafkaDTO dto = mapPedidoParaKafkaDTO(pedido);
        pedidoProducerPort.enviarMensagem(dto);

        return response;
    }

    @Override
    public ResponseDto atualizarPedido(Integer id, AtualizarPedidoRequest request) {
        try {
            Pedido pedidoExistente = repositoryPort.buscarPedidoPorId(id);
            if (pedidoExistente == null) {
                throw new PedidoNotFoundException(ConstantUtils.PEDIDO_NAO_ENCONTRADO);
            }

            // Busca todos os itens existentes desse pedido
            List<PedidoItem> itensExistentes = buscarItensPedido(id);

            boolean allItemsUpdated = true;

            for (PedidoItemRequest itemReq : request.getItens()) {
                PedidoProdutoDto produto = chamadaProdutoClient(itemReq.getSkuProduto());
                Integer idProduto = produto.getIdProduto();

                // Busca o item já existente pelo idPedido e idProduto
                PedidoItem itemExistente = itensExistentes.stream()
                        .filter(item -> item.getIdProduto().equals(idProduto))
                        .findFirst()
                        .orElse(null);

                if (itemExistente != null) {
                    itemExistente.setQuantidadeItem(itemReq.getQuantidadeItem());
                    itemExistente.setPrecoUnitarioItem(produto.getPrecoProduto());
                    boolean updated = pedidoItemRepositoryPort.atualizarPedidoItem(itemExistente);
                    if (!updated) {
                        allItemsUpdated = false;
                        break; // Para na primeira falha
                    }
                }
            }

            if (!allItemsUpdated) {
                throw new ErroInternoException("Erro ao atualizar um ou mais itens do pedido.");
            }

            // Busca todos os itens atualizados para calcular o novo valor total
            List<PedidoItem> itensAtualizados = buscarItensPedido(id);
            BigDecimal valorTotalAtualizado = calcularValorTotal(itensAtualizados);
            pedidoExistente.setValorTotalPedido(valorTotalAtualizado);

            // Atualiza o pedido no banco
            ResponseDto response = repositoryPort.atualizarPedido(pedidoExistente);
            return response;

        } catch (PedidoNotFoundException e) {
            log.error("Pedido não encontrado para o ID: {}", id);
            throw e;
        } catch (Exception e) {
            log.error("Erro ao atualizar pedido: {}", e.getMessage());
            throw new ErroInternoException("Erro ao atualizar pedido: " + e.getMessage());
        }
    }

    @Override
    public ResponseDto atualizaStatusPedido(Integer id, PedidoStatus novoStatus) {
        try {
            Pedido pedidoExistente = repositoryPort.buscarPedidoPorId(id);
            pedidoExistente.setStatusPedido(novoStatus);

            return repositoryPort.atualizarPedido(pedidoExistente);
        } catch (Exception e) {
            log.error("Erro ao atualizar status do pedido: {}", e.getMessage());
            throw new ErroInternoException("Erro ao atualizar status do pedido: " + e.getMessage());
        }

    }

    private List<PedidoItem> buscarItensPedido(Integer id) {
        try {
            List<PedidoItem> itens = pedidoItemRepositoryPort.buscarItensPedido(id);
            if (itens == null || itens.isEmpty()) {
                log.warn("Nenhum item encontrado para o pedido: {}", id);
                throw new InvalidPedidoException(ConstantUtils.ITENS_PEDIDO_NAO_ENCONTRADOS);
            }
            return itens;
        } catch (InvalidPedidoException e) {
            throw e;
        } catch (Exception e) {
            log.error("Erro ao buscar itens do pedido: {}", e.getMessage());
            throw new ErroInternoException("Erro ao buscar itens do pedido: " + e.getMessage());
        }
    }

    private Integer buscarIdCliente(String cpfCliente) {
        try {
            PedidoClienteDto pedidoClienteDto = pedidoClienteClient.buscarPorCpf(cpfCliente);
            return pedidoClienteDto.getIdCliente();
        } catch (feign.FeignException.NotFound e) {
            log.warn("Cliente não encontrado para o CPF: {}", cpfCliente);
            throw new InvalidPedidoException(ConstantUtils.CLIENTE_NAO_ENCONTRADO);
        } catch (Exception e) {
            log.error("Erro ao chamar o serviço de cliente: {}", e.getMessage());
            throw new ErroInternoException("Erro ao buscar cliente: " + e.getMessage());
        }
    }

    private List<PedidoItem> montarItensPedido(List<PedidoItemRequest> itensRequest) {
        List<PedidoItem> itens = new ArrayList<>();
        for (PedidoItemRequest itemReq : itensRequest) {
            PedidoProdutoDto produto = chamadaProdutoClient(itemReq.getSkuProduto());
            PedidoItem item = new PedidoItem(
                    null,
                    produto.getIdProduto(),
                    itemReq.getQuantidadeItem(),
                    produto.getPrecoProduto()
            );
            itens.add(item);
        }
        return itens;
    }

    private Pedido montarPedido(Integer idCliente, List<PedidoItem> itens) {
        BigDecimal valorTotal = calcularValorTotal(itens);

        return new Pedido(
                null,
                idCliente,
                PedidoStatus.ABERTO,
                LocalDateTime.now(),
                valorTotal,
                itens
        );
    }

    private BigDecimal calcularValorTotal(List<PedidoItem> itens) {
        return itens.stream()
                .map(i -> i.getPrecoUnitarioItem().multiply(BigDecimal.valueOf(i.getQuantidadeItem())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @SuppressWarnings("unchecked")
    private Integer buscaIdPedido(ResponseDto response) {
        Map<String, Object> data = (Map<String, Object>) response.getData();
        Integer idPedido = (Integer) data.get("idPedido");
        return idPedido;
    }

    private PedidoProdutoDto chamadaProdutoClient(String skuProduto) {
        try {
            return produtoClient.buscarPorSku(skuProduto);
        } catch (feign.FeignException.NotFound e) {
            log.warn("Produto não encontrado para o SKU: {}", skuProduto);
            throw new PedidoProdutoNotFoundException(ConstantUtils.PRODUTO_NAO_ENCONTRADO);
        } catch (Exception e) {
            log.error("Erro ao chamar o serviço de produto: {}", e.getMessage());
            throw new ErroInternoException("Erro ao buscar produto: " + e.getMessage());
        }
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

    private PedidoKafkaDTO mapPedidoParaKafkaDTO(Pedido pedido) {
        List<PedidoItemKafkaDTO> itensKafka = new ArrayList<>();
        for (PedidoItem item : pedido.getItens()) {
            PedidoItemKafkaDTO itemKafka = new PedidoItemKafkaDTO(
                    item.getIdProduto(),
                    item.getQuantidadeItem(),
                    item.getPrecoUnitarioItem()
            );
            itensKafka.add(itemKafka);
        }
        return new PedidoKafkaDTO(
                pedido.getIdPedido(),
                pedido.getIdCliente(),
                pedido.getValorTotalPedido(),
                itensKafka
        );
    }


}
