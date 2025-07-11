package com.fiap.postech.pedidohub.pedido.api.dto.kafka;

import com.fiap.postech.pedidohub.pedido.domain.model.PedidoItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoKafkaDTO {
    private Integer idPedido;
    private Integer idCliente;
    private BigDecimal valorTotalPedido;
    private List<PedidoItemKafkaDTO> itens;
}
