package com.fiap.postech.pedidohub.pedido.gateway.database.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "pedido_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido_item")
    private Integer idPedidoItem;

    @Column(name = "id_pedido", nullable = false)
    private Integer idPedido;

    @Column(name = "id_produto", nullable = false)
    private Integer idProduto;

    @Column(name = "quantidade_item", nullable = false)
    private Integer quantidadeItem;

    @Column(name = "preco_unitario_item", nullable = false)
    private BigDecimal precoUnitarioItem;
}