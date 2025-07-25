package com.fiap.postech.pedidohub.pedido.api.dto.client.produto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PedidoProdutoDto {

    private Integer idProduto;
    private String skuProduto;
    private BigDecimal precoProduto;
}
