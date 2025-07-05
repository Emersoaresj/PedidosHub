package com.fiap.postech.pedidohub.produto.gateway.database.repository;

import com.fiap.postech.pedidohub.produto.gateway.database.entity.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepositoryJPA extends JpaRepository<ProdutoEntity, Integer> {

    Optional<ProdutoEntity> findBySkuProduto(String sku);

}
