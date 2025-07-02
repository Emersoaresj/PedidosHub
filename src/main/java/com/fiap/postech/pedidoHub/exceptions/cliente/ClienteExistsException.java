package com.fiap.postech.pedidoHub.exceptions.cliente;

public class ClienteExistsException extends RuntimeException {
    public ClienteExistsException(String message) {
        super(message);
    }
}
