package com.fiap.postech.pedidohub.pedido.gateway.kafka.producer;

import com.fiap.postech.pedidohub.pedido.api.dto.kafka.PedidoKafkaDTO;
import com.fiap.postech.pedidohub.pedido.gateway.kafka.port.PedidoProducerPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PedidoProducerImpl implements PedidoProducerPort {


    private static final String TOPICO = "pedidos-criados";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PedidoProducerImpl(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    @Override
    public void enviarMensagem(PedidoKafkaDTO dto) {
        kafkaTemplate.send(TOPICO, dto);
        log.info("Mensagem enviada para o tópico {}: {}", TOPICO, dto);
    }
}
