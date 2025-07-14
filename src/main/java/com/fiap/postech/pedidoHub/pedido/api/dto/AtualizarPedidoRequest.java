package com.fiap.postech.pedidohub.pedido.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class AtualizarPedidoRequest {

    private List<PedidoItemRequest> itens;
}
