package com.fiap.postech.pedidohub.estoque.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class BaixaEstoqueRequest {
    private List<ItemEstoqueBaixaDTO> itens;
}
