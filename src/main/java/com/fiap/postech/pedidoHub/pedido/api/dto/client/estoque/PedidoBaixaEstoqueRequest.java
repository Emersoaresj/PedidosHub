package com.fiap.postech.pedidohub.pedido.api.dto.client.estoque;

import lombok.Data;

import java.util.List;

@Data
public class PedidoBaixaEstoqueRequest {
    private List<PedidoItemEstoqueBaixaDTO> itens;
}
