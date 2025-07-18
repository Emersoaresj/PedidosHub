package com.fiap.postech.pedidohub.produto.api.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProdutoDto {

    private Integer idProduto;
    private String skuProduto;
    private BigDecimal precoProduto;
}
