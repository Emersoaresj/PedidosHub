package com.fiap.postech.pedidohub.pagamento.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoRequest {
    private Integer idPedido;
    private Integer idCliente;
    private BigDecimal valorTotalPedido;
}
