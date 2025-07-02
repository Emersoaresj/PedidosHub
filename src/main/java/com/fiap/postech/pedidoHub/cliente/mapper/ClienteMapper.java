package com.fiap.postech.pedidoHub.cliente.mapper;

import com.fiap.postech.pedidoHub.cliente.domain.Cliente;
import com.fiap.postech.pedidoHub.cliente.gateway.database.entity.ClienteEntity;
import com.fiap.postech.pedidoHub.cliente.dto.ClienteRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    @Mapping(target = "id", ignore = true)
    ClienteEntity domainToEntity(Cliente cliente);

    Cliente entityToDomain(ClienteEntity entity);

    Cliente requestToDomain(ClienteRequest request);

}
