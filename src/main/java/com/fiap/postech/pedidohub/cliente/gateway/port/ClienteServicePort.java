package com.fiap.postech.pedidohub.cliente.gateway.port;

import com.fiap.postech.pedidohub.cliente.api.dto.ClienteDto;
import com.fiap.postech.pedidohub.cliente.api.dto.ClienteRequest;

public interface ClienteServicePort {

    ClienteDto cadastraCliente(ClienteRequest request);
}
