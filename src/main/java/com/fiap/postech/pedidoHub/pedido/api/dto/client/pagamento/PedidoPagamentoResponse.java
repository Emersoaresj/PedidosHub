package com.fiap.postech.pedidohub.pedido.api.dto.client.pagamento;


import lombok.Data;

@Data
public class PedidoPagamentoResponse {
    private boolean aprovado;
    private String mensagem;
}
