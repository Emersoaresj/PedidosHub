package com.fiap.postech.pedidohub.estoque.domain.exceptions;

public class InvalidSkuEstoqueException extends RuntimeException {
    public InvalidSkuEstoqueException(String message) {
        super(message);
    }
}
