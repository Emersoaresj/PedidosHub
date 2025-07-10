package com.fiap.postech.pedidohub.cliente.domain.exceptions;

public class ClienteNotFoundException extends RuntimeException {
    public ClienteNotFoundException(String message) {
        super(message);
    }
}
