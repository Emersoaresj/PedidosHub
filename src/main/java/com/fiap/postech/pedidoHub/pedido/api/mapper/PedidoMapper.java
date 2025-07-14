package com.fiap.postech.pedidohub.pedido.api.mapper;

import com.fiap.postech.pedidohub.pedido.domain.model.Pedido;
import com.fiap.postech.pedidohub.pedido.domain.model.PedidoItem;
import com.fiap.postech.pedidohub.pedido.gateway.database.entity.PedidoEntity;
import com.fiap.postech.pedidohub.pedido.gateway.database.entity.PedidoItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PedidoMapper {

    PedidoMapper INSTANCE = Mappers.getMapper(PedidoMapper.class);

    @Mapping(target = "idPedido", ignore = true)
    PedidoEntity domainToEntityCreate(Pedido pedido);

    PedidoEntity domainToEntityUpdate(Pedido pedido);

    @Mapping(target = "idPedidoItem", ignore = true)
    @Mapping(target = "idPedido", ignore = true)
    PedidoItemEntity domainToItemEntity(PedidoItem item);

    @Mapping(target = "itens", ignore = true)
    Pedido entityToDomain (PedidoEntity pedidoEntity);
}
