package com.fiap.postech.pedidohub.produto.domain.exceptions;

public class ProdutoExistsException extends RuntimeException {
    public ProdutoExistsException(String message) {
        super(message);
    }
}
