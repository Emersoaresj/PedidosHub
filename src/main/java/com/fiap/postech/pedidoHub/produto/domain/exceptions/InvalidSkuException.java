package com.fiap.postech.pedidohub.produto.domain.exceptions;

public class InvalidSkuException extends RuntimeException {
    public InvalidSkuException(String message) {
        super(message);
    }
}
