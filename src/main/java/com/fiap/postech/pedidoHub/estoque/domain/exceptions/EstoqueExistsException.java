package com.fiap.postech.pedidohub.estoque.domain.exceptions;

public class EstoqueExistsException extends RuntimeException {
    public EstoqueExistsException(String message) {
        super(message);
    }
}
