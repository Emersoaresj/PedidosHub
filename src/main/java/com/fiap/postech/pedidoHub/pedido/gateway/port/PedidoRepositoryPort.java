package com.fiap.postech.pedidohub.pedido.gateway.port;

import com.fiap.postech.pedidohub.pedido.domain.model.Pedido;
import com.fiap.postech.pedidohub.commom.utils.ResponseDto;

public interface PedidoRepositoryPort {

    ResponseDto cadastrarPedido(Pedido pedido);

}
