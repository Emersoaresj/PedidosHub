package com.fiap.postech.pedidohub.pedido.domain.exceptions;

public class PedidoProdutoNotFoundException extends RuntimeException {
    public PedidoProdutoNotFoundException(String message) {
        super(message);
    }
}
