package com.fiap.postech.pedidohub.pedido.gateway.port;

import com.fiap.postech.pedidohub.pedido.api.dto.PedidoRequest;
import com.fiap.postech.pedidohub.commom.utils.ResponseDto;

public interface PedidoServicePort {

    ResponseDto cadastrarPedido(PedidoRequest request);

}
