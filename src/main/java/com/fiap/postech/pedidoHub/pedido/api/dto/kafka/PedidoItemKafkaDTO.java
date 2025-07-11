package com.fiap.postech.pedidohub.pedido.api.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoItemKafkaDTO {
    private Integer idProduto;
    private Integer quantidadeItem;
    private BigDecimal precoUnitarioItem;
}
