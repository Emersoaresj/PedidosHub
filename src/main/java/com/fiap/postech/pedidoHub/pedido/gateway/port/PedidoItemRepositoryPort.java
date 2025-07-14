package com.fiap.postech.pedidohub.pedido.gateway.port;

import com.fiap.postech.pedidohub.pedido.domain.model.PedidoItem;

import java.util.List;

public interface PedidoItemRepositoryPort {

    List<PedidoItem> buscarItensPedido(Integer idPedido);

    boolean atualizarPedidoItem(PedidoItem pedidoItem);

}
