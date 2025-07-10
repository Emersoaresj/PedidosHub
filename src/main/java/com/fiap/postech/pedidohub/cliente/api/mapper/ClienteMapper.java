package com.fiap.postech.pedidohub.cliente.api.mapper;

import com.fiap.postech.pedidohub.cliente.api.dto.ClienteDto;
import com.fiap.postech.pedidohub.cliente.domain.model.Cliente;
import com.fiap.postech.pedidohub.cliente.gateway.database.entity.ClienteEntity;
import com.fiap.postech.pedidohub.cliente.api.dto.ClienteRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    @Mapping(target = "idCliente", ignore = true)
    ClienteEntity domainToEntity(Cliente cliente);

    Cliente entityToDomain(ClienteEntity entity);

    Cliente requestToDomain(ClienteRequest request);

    ClienteDto domainToDto(Cliente cliente);

}
