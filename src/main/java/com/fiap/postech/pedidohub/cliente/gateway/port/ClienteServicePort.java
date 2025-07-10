package com.fiap.postech.pedidohub.cliente.gateway.port;

import com.fiap.postech.pedidohub.cliente.api.dto.ClienteDto;
import com.fiap.postech.pedidohub.cliente.api.dto.ClienteRequest;
import com.fiap.postech.pedidohub.commom.utils.ResponseDto;

public interface ClienteServicePort {

    ResponseDto cadastraCliente(ClienteRequest request);
    ClienteDto buscarClientePorCpf(String cpf);
}
