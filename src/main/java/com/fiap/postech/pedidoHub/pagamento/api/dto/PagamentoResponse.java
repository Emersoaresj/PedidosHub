package com.fiap.postech.pedidohub.pagamento.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoResponse {
    private boolean aprovado;
    private String mensagem;
}
