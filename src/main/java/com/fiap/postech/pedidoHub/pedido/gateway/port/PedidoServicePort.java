package com.fiap.postech.pedidohub.pedido.gateway.port;

import com.fiap.postech.pedidohub.pedido.api.dto.AtualizarPedidoRequest;
import com.fiap.postech.pedidohub.pedido.api.dto.PedidoRequest;
import com.fiap.postech.pedidohub.commom.utils.ResponseDto;
import com.fiap.postech.pedidohub.pedido.api.dto.PedidoStatus;

public interface PedidoServicePort {

    ResponseDto cadastrarPedido(PedidoRequest request);

    ResponseDto atualizarPedido(Integer id, AtualizarPedidoRequest request);

    ResponseDto atualizaStatusPedido(Integer id, PedidoStatus novoStatus);

}
