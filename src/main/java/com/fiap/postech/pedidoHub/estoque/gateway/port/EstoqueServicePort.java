package com.fiap.postech.pedidohub.estoque.gateway.port;

import com.fiap.postech.pedidohub.estoque.api.dto.AtualizaEstoqueRequest;
import com.fiap.postech.pedidohub.estoque.api.dto.BaixaEstoqueRequest;
import com.fiap.postech.pedidohub.estoque.api.dto.BaixaEstoqueResponse;
import com.fiap.postech.pedidohub.estoque.api.dto.EstoqueRequest;
import com.fiap.postech.pedidohub.commom.utils.ResponseDto;

public interface EstoqueServicePort {

    ResponseDto cadastrarEstoque(EstoqueRequest request);

    ResponseDto atualizarEstoque(AtualizaEstoqueRequest request);

    BaixaEstoqueResponse baixarEstoque(BaixaEstoqueRequest request);

    BaixaEstoqueResponse restaurarEstoque(BaixaEstoqueRequest request);

}
