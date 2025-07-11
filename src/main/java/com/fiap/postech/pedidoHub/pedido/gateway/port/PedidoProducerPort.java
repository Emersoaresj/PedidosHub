package com.fiap.postech.pedidohub.pedido.gateway.port;

import com.fiap.postech.pedidohub.pedido.api.dto.kafka.PedidoKafkaDTO;

public interface PedidoProducerPort {

    void enviarMensagem(PedidoKafkaDTO dto);

}
