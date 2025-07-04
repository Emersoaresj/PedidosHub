package com.fiap.postech.pedidohub.cliente.gateway.database.repository;

import com.fiap.postech.pedidohub.cliente.gateway.database.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepositoryJPA extends JpaRepository<ClienteEntity, Integer> {

    Optional<ClienteEntity> findByCpfCliente(String cpf);

}
