package com.fiap.postech.pedidohub.cliente.domain.exceptions;

public class InvalidCpfException extends RuntimeException {
    public InvalidCpfException(String message) {
        super(message);
    }
}
