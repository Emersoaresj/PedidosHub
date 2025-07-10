package com.fiap.postech.pedidohub.pedido.domain.exceptions;

public class InvalidPedidoException extends RuntimeException {
    public InvalidPedidoException(String message) {
        super(message);
    }
}
