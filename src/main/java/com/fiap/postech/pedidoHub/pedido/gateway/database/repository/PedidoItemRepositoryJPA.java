package com.fiap.postech.pedidohub.pedido.gateway.database.repository;

import com.fiap.postech.pedidohub.pedido.gateway.database.entity.PedidoItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoItemRepositoryJPA extends JpaRepository<PedidoItemEntity, Integer> {

    List<PedidoItemEntity> findAllByIdPedido(Integer idPedido);
}
