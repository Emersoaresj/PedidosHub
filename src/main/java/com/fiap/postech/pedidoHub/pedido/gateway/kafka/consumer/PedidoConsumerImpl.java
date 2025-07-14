package com.fiap.postech.pedidohub.pedido.gateway.kafka.consumer;

import com.fiap.postech.pedidohub.commom.utils.ConstantUtils;
import com.fiap.postech.pedidohub.pedido.api.dto.PedidoStatus;
import com.fiap.postech.pedidohub.pedido.api.dto.client.estoque.PedidoBaixaEstoqueRequest;
import com.fiap.postech.pedidohub.pedido.api.dto.client.estoque.PedidoBaixaEstoqueResponse;
import com.fiap.postech.pedidohub.pedido.api.dto.client.estoque.PedidoItemEstoqueBaixaDTO;
import com.fiap.postech.pedidohub.pedido.api.dto.client.pagamento.PedidoPagamentoRequest;
import com.fiap.postech.pedidohub.pedido.api.dto.client.pagamento.PedidoPagamentoResponse;
import com.fiap.postech.pedidohub.pedido.api.dto.kafka.PedidoItemKafkaDTO;
import com.fiap.postech.pedidohub.pedido.api.dto.kafka.PedidoKafkaDTO;
import com.fiap.postech.pedidohub.pedido.domain.exceptions.PedidoNotFoundException;
import com.fiap.postech.pedidohub.pedido.domain.model.Pedido;
import com.fiap.postech.pedidohub.pedido.domain.model.PedidoItem;
import com.fiap.postech.pedidohub.pedido.gateway.client.PedidoEstoqueClient;
import com.fiap.postech.pedidohub.pedido.gateway.client.PedidoPagamentoClient;
import com.fiap.postech.pedidohub.pedido.gateway.port.PedidoItemRepositoryPort;
import com.fiap.postech.pedidohub.pedido.gateway.port.PedidoRepositoryPort;
import com.fiap.postech.pedidohub.pedido.gateway.port.PedidoServicePort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PedidoConsumerImpl {

    private static final String TOPICO = "pedidos-criados";
    private static final String GRUPO = "pedido-group";

    @Autowired
    private PedidoRepositoryPort pedidoRepositoryPort;
    @Autowired
    private PedidoItemRepositoryPort pedidoItemRepositoryPort;

    @Autowired
    private PedidoServicePort pedidoServicePort;

    @Autowired
    private PedidoEstoqueClient estoqueClient;
    @Autowired
    private PedidoPagamentoClient pagamentoClient;


    @KafkaListener(topics = TOPICO, groupId = GRUPO, containerFactory = "kafkaListenerContainerFactory")
    public void processarPedido(PedidoKafkaDTO pedidoKafkaDTO) {
        log.info("Recebido pedido do Kafka: {}", pedidoKafkaDTO);

        // 1. Buscar pedido e itens do pedido no banco
        Pedido pedido = pedidoRepositoryPort.buscarPedidoPorId(pedidoKafkaDTO.getIdPedido());
        if (pedido == null) {
            log.error("Pedido não encontrado: {}", pedidoKafkaDTO.getIdPedido());
            throw new PedidoNotFoundException(ConstantUtils.PEDIDO_NAO_ENCONTRADO);
        }

        List<PedidoItem> pedidoItem = pedidoItemRepositoryPort.buscarItensPedido(pedido.getIdPedido());
        pedido.setItens(pedidoItem);

        // 2. Validar e baixar estoque
        PedidoBaixaEstoqueRequest baixaRequest = mapParaRequestEstoque(pedidoKafkaDTO.getItens());
        PedidoBaixaEstoqueResponse resposta = estoqueClient.baixaEstoque(baixaRequest);

        if (!resposta.isSucesso()) {
            pedido.setStatusPedido(PedidoStatus.FECHADO_SEM_ESTOQUE);
            pedidoServicePort.atualizaStatusPedido(pedidoKafkaDTO.getIdPedido(), PedidoStatus.FECHADO_SEM_ESTOQUE);
            log.info("Estoque insuficiente para pedido {}", pedido.getIdPedido());
            return;
        }


        // 3. Processar pagamento
        PedidoPagamentoRequest requestPagamento = mapParaRequestPagamento(pedidoKafkaDTO);
        PedidoPagamentoResponse pagamentoResponse = pagamentoClient.processarPagamento(requestPagamento);

        if (!pagamentoResponse.isAprovado()) {
            // Restabelece estoque se pagamento foi recusado
            PedidoBaixaEstoqueRequest restauraRequest = mapParaRequestEstoque(pedidoKafkaDTO.getItens());
            estoqueClient.restaurarEstoque(restauraRequest);
            pedido.setStatusPedido(PedidoStatus.FECHADO_SEM_CREDITO);
            pedidoServicePort.atualizaStatusPedido(pedidoKafkaDTO.getIdPedido(), PedidoStatus.FECHADO_SEM_CREDITO);
            log.info("Pagamento recusado para pedido {}", pedido.getIdPedido());
            return;
        }

        // 4. Tudo OK
        pedido.setStatusPedido(PedidoStatus.FECHADO_COM_SUCESSO);
        pedidoServicePort.atualizaStatusPedido(pedidoKafkaDTO.getIdPedido(), PedidoStatus.FECHADO_COM_SUCESSO);
        log.info("Pedido {} finalizado com sucesso", pedido.getIdPedido());
    }

    private PedidoBaixaEstoqueRequest mapParaRequestEstoque(List<PedidoItemKafkaDTO> itensKafka) {
        List<PedidoItemEstoqueBaixaDTO> itensEstoque = new ArrayList<>();
        for (PedidoItemKafkaDTO item : itensKafka) {
            PedidoItemEstoqueBaixaDTO estoqueItem = new PedidoItemEstoqueBaixaDTO();
            estoqueItem.setIdProduto(item.getIdProduto());
            estoqueItem.setQuantidadeItem(item.getQuantidadeItem());
            itensEstoque.add(estoqueItem);
        }
        PedidoBaixaEstoqueRequest request = new PedidoBaixaEstoqueRequest();
        request.setItens(itensEstoque);
        return request;
    }

    private PedidoPagamentoRequest mapParaRequestPagamento(PedidoKafkaDTO pedidoKafkaDTO) {
        PedidoPagamentoRequest request = new PedidoPagamentoRequest();
        request.setIdPedido(pedidoKafkaDTO.getIdPedido());
        request.setIdCliente(pedidoKafkaDTO.getIdCliente());
        request.setValorTotalPedido(pedidoKafkaDTO.getValorTotalPedido());
        return request;
    }
}
