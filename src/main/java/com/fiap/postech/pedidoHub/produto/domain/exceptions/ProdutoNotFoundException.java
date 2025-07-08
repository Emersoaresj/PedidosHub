package com.fiap.postech.pedidohub.produto.domain.exceptions;

public class ProdutoNotFoundException extends RuntimeException
{
    public ProdutoNotFoundException(String message) {
        super(message);
    }
}
