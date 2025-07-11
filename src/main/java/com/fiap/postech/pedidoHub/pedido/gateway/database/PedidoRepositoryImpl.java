package com.fiap.postech.pedidohub.pedido.gateway.database;

import com.fiap.postech.pedidohub.commom.config.ErroInternoException;
import com.fiap.postech.pedidohub.pedido.api.mapper.PedidoMapper;
import com.fiap.postech.pedidohub.pedido.domain.model.Pedido;
import com.fiap.postech.pedidohub.pedido.domain.model.PedidoItem;
import com.fiap.postech.pedidohub.pedido.gateway.port.PedidoRepositoryPort;
import com.fiap.postech.pedidohub.pedido.gateway.database.entity.PedidoEntity;
import com.fiap.postech.pedidohub.pedido.gateway.database.entity.PedidoItemEntity;
import com.fiap.postech.pedidohub.pedido.gateway.database.repository.PedidoItemRepositoryJPA;
import com.fiap.postech.pedidohub.pedido.gateway.database.repository.PedidoRepositoryJPA;
import com.fiap.postech.pedidohub.commom.utils.ConstantUtils;
import com.fiap.postech.pedidohub.commom.utils.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Repository
public class PedidoRepositoryImpl implements PedidoRepositoryPort {


    @Autowired
    private PedidoRepositoryJPA pedidoRepositoryJPA;

    @Autowired
    private PedidoItemRepositoryJPA pedidoItemRepositoryJPA;

    @Override
    public ResponseDto cadastrarPedido(Pedido pedido) {
        try {
            PedidoEntity pedidoEntity = PedidoMapper.INSTANCE.domainToEntity(pedido);
            PedidoEntity savedPedido = pedidoRepositoryJPA.save(pedidoEntity);

            for (PedidoItem item : pedido.getItens()) {
                PedidoItemEntity itemEntity = PedidoMapper.INSTANCE.domainToItemEntity(item);
                itemEntity.setIdPedido(savedPedido.getIdPedido());
                pedidoItemRepositoryJPA.save(itemEntity);
            }

            return montaResponse(savedPedido);
        } catch (Exception e) {
            log.error("Erro ao cadastrar pedido", e);
            throw new ErroInternoException("Erro ao cadastrar pedido: " + e.getMessage());
        }
    }

    private ResponseDto montaResponse(PedidoEntity pedidoEntity) {
        ResponseDto response = new ResponseDto();
        response.setMessage(ConstantUtils.PEDIDO_CADASTRADO);

        Map<String, Object> data = new HashMap<>();
        data.put("idPedido", pedidoEntity.getIdPedido());
        data.put("idCliente", pedidoEntity.getIdCliente());
        data.put("statusPedido", pedidoEntity.getStatusPedido());
        data.put("dataPedido", pedidoEntity.getDataPedido());
        data.put("valorTotalPedido", pedidoEntity.getValorTotalPedido());

        response.setData(data);
        return response;
    }

}
