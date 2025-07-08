package com.fiap.postech.pedidohub.estoque.gateway.port;

import com.fiap.postech.pedidohub.estoque.domain.model.Estoque;
import com.fiap.postech.pedidohub.utils.ResponseDto;

public interface EstoqueRepositoryPort {

    ResponseDto cadastrarEstoque(Estoque estoque);

    boolean existeEstoquePorIdProduto(Integer idProduto);

}
