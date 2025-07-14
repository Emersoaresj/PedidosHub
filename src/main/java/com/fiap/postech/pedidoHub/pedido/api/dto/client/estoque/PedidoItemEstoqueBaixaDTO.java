package com.fiap.postech.pedidohub.pedido.api.dto.client.estoque;

import lombok.Data;

@Data
public class PedidoItemEstoqueBaixaDTO {

    private Integer idProduto;
    private Integer quantidadeItem;
}
