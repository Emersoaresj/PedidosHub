package com.fiap.postech.pedidohub.pedido.api.dto.client.estoque;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoBaixaEstoqueResponse {
    private boolean sucesso;
    private String mensagem;
}
