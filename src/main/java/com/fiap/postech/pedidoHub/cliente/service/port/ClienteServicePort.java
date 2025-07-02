package com.fiap.postech.pedidoHub.cliente.service.port;

import com.fiap.postech.pedidoHub.cliente.dto.ClienteDto;
import com.fiap.postech.pedidoHub.cliente.dto.ClienteRequest;

public interface ClienteServicePort {

    ClienteDto cadastraCliente(ClienteRequest request);
}
