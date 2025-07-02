package com.fiap.postech.pedidoHub.cliente.gateway.database.repository;

import com.fiap.postech.pedidoHub.cliente.gateway.database.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer> {

    Optional<ClienteEntity> findByCpfCliente(String cpf);

}
