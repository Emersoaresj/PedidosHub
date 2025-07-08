package com.fiap.postech.pedidohub.produto.gateway.port;

import com.fiap.postech.pedidohub.produto.api.dto.ProdutoDto;
import com.fiap.postech.pedidohub.utils.ResponseDto;
import com.fiap.postech.pedidohub.produto.api.dto.ProdutoRequest;

public interface ProdutoServicePort {

    ResponseDto cadastrarProduto(ProdutoRequest request);

    ProdutoDto buscarPorSku(String sku);

}
