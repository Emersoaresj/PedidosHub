package com.fiap.postech.pedidohub.cliente.gateway.port;

import com.fiap.postech.pedidohub.cliente.domain.model.Cliente;
import com.fiap.postech.pedidohub.cliente.api.dto.ClienteDto;

import java.util.Optional;

public interface ClienteRepositoryPort {

    ClienteDto cadastraCliente(Cliente request);

    Optional<Cliente> findByCpfCliente(String cpf);

}
