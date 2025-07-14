package com.fiap.postech.pedidohub.pedido.gateway.port;

import com.fiap.postech.pedidohub.commom.utils.ResponseDto;
import com.fiap.postech.pedidohub.pedido.domain.model.Pedido;

public interface PedidoRepositoryPort {

    ResponseDto cadastrarPedido(Pedido pedido);

    Pedido buscarPedidoPorId(Integer idPedido);

    ResponseDto atualizarPedido(Pedido pedido);

}
