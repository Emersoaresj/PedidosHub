package com.fiap.postech.pedidohub.pedido.gateway.database.repository;

import com.fiap.postech.pedidohub.commom.config.ErroInternoException;
import com.fiap.postech.pedidohub.pedido.api.mapper.PedidoItemMapper;
import com.fiap.postech.pedidohub.pedido.domain.model.PedidoItem;
import com.fiap.postech.pedidohub.pedido.gateway.database.entity.PedidoItemEntity;
import com.fiap.postech.pedidohub.pedido.gateway.port.PedidoItemRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class PedidoItemRepositoryImpl implements PedidoItemRepositoryPort {

    @Autowired
    private PedidoItemRepositoryJPA pedidoItemRepositoryJPA;


    @Override
    public List<PedidoItem> buscarItensPedido(Integer idPedido) {
        try {
            List<PedidoItemEntity> entity = pedidoItemRepositoryJPA.findAllByIdPedido(idPedido);
            return PedidoItemMapper.INSTANCE.entityToDomain(entity);
        } catch (Exception e) {
            throw new ErroInternoException("Erro ao buscar itens do pedido: " + e.getMessage());
        }

    }

    @Override
    public boolean atualizarPedidoItem(PedidoItem item) {
        try {
            PedidoItemEntity entity = PedidoItemMapper.INSTANCE.domainToEntity(item);
            pedidoItemRepositoryJPA.save(entity);
            return true;
        } catch (Exception e) {
            log.error("Erro ao atualizar item do pedido", e);
            return false;
        }
    }
}
