package com.fiap.postech.pedidohub.estoque.gateway.port;

import com.fiap.postech.pedidohub.estoque.api.dto.EstoqueRequest;
import com.fiap.postech.pedidohub.utils.ResponseDto;

public interface EstoqueServicePort {

    ResponseDto cadastrarEstoque(EstoqueRequest request);

}
