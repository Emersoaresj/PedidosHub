package com.fiap.postech.pedidohub.pedido.domain.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoItem {
    private Integer idPedidoItem;
    private Integer idProduto;
    private Integer quantidadeItem;
    private BigDecimal precoUnitarioItem;
}
