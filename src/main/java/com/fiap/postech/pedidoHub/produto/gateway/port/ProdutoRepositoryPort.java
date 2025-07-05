package com.fiap.postech.pedidohub.produto.gateway.port;

import com.fiap.postech.pedidohub.utils.ResponseDto;
import com.fiap.postech.pedidohub.produto.domain.model.Produto;

import java.util.Optional;

public interface ProdutoRepositoryPort {

    ResponseDto cadastrarProduto(Produto produto);

    Optional<Produto> findBySkuProduto(String sku);
}
