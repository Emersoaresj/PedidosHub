package com.fiap.postech.pedidohub.cliente.domain.exceptions;

public class ClienteExistsException extends RuntimeException {
    public ClienteExistsException(String message) {
        super(message);
    }
}
