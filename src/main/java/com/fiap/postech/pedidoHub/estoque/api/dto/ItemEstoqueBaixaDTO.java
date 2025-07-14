package com.fiap.postech.pedidohub.estoque.api.dto;

import lombok.Data;

@Data
public class ItemEstoqueBaixaDTO {
    private Integer idProduto;
    private Integer quantidade;
}