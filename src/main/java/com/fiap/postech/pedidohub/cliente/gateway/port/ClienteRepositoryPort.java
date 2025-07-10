package com.fiap.postech.pedidohub.cliente.gateway.port;

import com.fiap.postech.pedidohub.cliente.domain.model.Cliente;
import com.fiap.postech.pedidohub.commom.utils.ResponseDto;

import java.util.Optional;

public interface ClienteRepositoryPort {

    ResponseDto cadastraCliente(Cliente request);

    Optional<Cliente> findByCpfCliente(String cpf);

}
