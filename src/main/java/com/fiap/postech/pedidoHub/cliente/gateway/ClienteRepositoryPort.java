package com.fiap.postech.pedidoHub.cliente.gateway;

import com.fiap.postech.pedidoHub.cliente.domain.Cliente;
import com.fiap.postech.pedidoHub.cliente.dto.ClienteDto;

import java.util.Optional;

public interface ClienteRepositoryPort {

    ClienteDto cadastraCliente(Cliente request);

    Optional<Cliente> findByCpfCliente(String cpf);

}
