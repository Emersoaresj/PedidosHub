package com.fiap.postech.pedidohub.estoque.domain.exceptions;

public class EstoqueNotFoundException extends RuntimeException {
    public EstoqueNotFoundException(String message) {
        super(message);
    }
}
