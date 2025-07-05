package com.fiap.postech.pedidohub.produto.api.mapper;

import com.fiap.postech.pedidohub.produto.api.dto.ProdutoRequest;
import com.fiap.postech.pedidohub.produto.domain.model.Produto;
import com.fiap.postech.pedidohub.produto.gateway.database.entity.ProdutoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProdutoMapper {

    ProdutoMapper INSTANCE = Mappers.getMapper(ProdutoMapper.class);

    Produto requestToDomain(ProdutoRequest request);

    @Mapping(target = "idProduto", ignore = true)
    ProdutoEntity domainToEntity(Produto produto);

    Produto entityToDomain(ProdutoEntity entity);
}
