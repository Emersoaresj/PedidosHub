package com.fiap.postech.pedidohub.estoque.domain.exceptions;

public class InvalidQuantidadeEstoqueException extends RuntimeException {
    public InvalidQuantidadeEstoqueException(String message) {
        super(message);
    }
}
