package com.fiap.postech.pedidohub.produto.domain.exceptions;

public class InvalidPrecoException extends RuntimeException {
    public InvalidPrecoException(String message) {
        super(message);
    }
}
