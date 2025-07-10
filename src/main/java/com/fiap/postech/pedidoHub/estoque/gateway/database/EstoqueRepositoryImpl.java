package com.fiap.postech.pedidohub.estoque.gateway.database;

import com.fiap.postech.pedidohub.commom.config.ErroInternoException;
import com.fiap.postech.pedidohub.estoque.domain.model.Estoque;
import com.fiap.postech.pedidohub.estoque.gateway.database.entity.EstoqueEntity;
import com.fiap.postech.pedidohub.estoque.gateway.database.repository.EstoqueRepositoryJPA;
import com.fiap.postech.pedidohub.estoque.gateway.port.EstoqueRepositoryPort;
import com.fiap.postech.pedidohub.estoque.api.mapper.EstoqueMapper;
import com.fiap.postech.pedidohub.commom.utils.ConstantUtils;
import com.fiap.postech.pedidohub.commom.utils.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Repository
public class EstoqueRepositoryImpl implements EstoqueRepositoryPort {

    @Autowired
    private EstoqueRepositoryJPA estoqueRepositoryJPA;


    @Override
    public ResponseDto cadastrarEstoque(Estoque estoque) {
        try {
            EstoqueEntity entity = EstoqueMapper.INSTANCE.domainToEntity(estoque);
            EstoqueEntity saved = estoqueRepositoryJPA.save(entity);
            log.info("Cadastrando estoque para o SKU: {}", estoque.getSkuProduto());
            return montaResponse(saved);
        } catch (Exception e) {
            log.error("Erro ao cadastrar estoque", e);
            throw new ErroInternoException("Erro ao cadastrar estoque: " + e.getMessage());
        }
    }

    @Override
    public boolean existeEstoquePorIdProduto(Integer idProduto) {
        try {
            return estoqueRepositoryJPA.existsByIdProduto(idProduto);
        } catch (Exception e) {
            log.error("Erro ao verificar existência de estoque para o produto ID: {}", idProduto, e);
            throw new ErroInternoException("Erro ao verificar existência de estoque: " + e.getMessage());
        }
    }

    private ResponseDto montaResponse(EstoqueEntity estoqueEntity) {
        ResponseDto response = new ResponseDto();
        response.setMessage(ConstantUtils.ESTOQUE_CADASTRADO);

        Map<String, Object> data = new HashMap<>();
        data.put("skuProduto", estoqueEntity.getSkuProduto());
        data.put("quantidadeEstoque", estoqueEntity.getQuantidadeEstoque());

        response.setData(data);
        return response;
    }

}
