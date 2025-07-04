package com.fiap.postech.pedidohub.cliente.domain.exceptions;

public class InvalidCepException extends RuntimeException
{
    public InvalidCepException(String message) {
        super(message);
    }
}
