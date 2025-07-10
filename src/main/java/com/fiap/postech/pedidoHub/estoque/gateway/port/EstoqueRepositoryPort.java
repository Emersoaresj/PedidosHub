package com.fiap.postech.pedidohub.estoque.gateway.port;

import com.fiap.postech.pedidohub.estoque.domain.model.Estoque;
import com.fiap.postech.pedidohub.commom.utils.ResponseDto;

public interface EstoqueRepositoryPort {

    ResponseDto cadastrarEstoque(Estoque estoque);

    boolean existeEstoquePorIdProduto(Integer idProduto);

}
