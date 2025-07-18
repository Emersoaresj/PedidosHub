package com.fiap.postech.pedidohub.produto.gateway.database;

import com.fiap.postech.pedidohub.commom.utils.ResponseDto;
import com.fiap.postech.pedidohub.commom.config.ErroInternoException;
import com.fiap.postech.pedidohub.produto.api.mapper.ProdutoMapper;
import com.fiap.postech.pedidohub.produto.domain.model.Produto;
import com.fiap.postech.pedidohub.produto.gateway.database.entity.ProdutoEntity;
import com.fiap.postech.pedidohub.produto.gateway.database.repository.ProdutoRepositoryJPA;
import com.fiap.postech.pedidohub.produto.gateway.port.ProdutoRepositoryPort;
import com.fiap.postech.pedidohub.commom.utils.ConstantUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class ProdutoRepositoryImpl implements ProdutoRepositoryPort {

    @Autowired
    private ProdutoRepositoryJPA produtoRepositoryJPA;

    @Override
    public ResponseDto cadastrarProduto(Produto produto) {
        try {
            ProdutoEntity entity = ProdutoMapper.INSTANCE.domainToEntity(produto);
            ProdutoEntity savedEntity = produtoRepositoryJPA.save(entity);
            return montaResponse(savedEntity);
        } catch (Exception e) {
            log.error("Erro ao cadastrar produto", e);
            throw new ErroInternoException("Erro ao cadastrar produto: " + e.getMessage());
        }
    }

    @Override
    public Optional<Produto> findBySkuProduto(String sku) {
        try {
            return produtoRepositoryJPA.findBySkuProduto(sku)
                    .map(ProdutoMapper.INSTANCE::entityToDomain);
        } catch (Exception e) {
            log.error("Erro ao buscar produto por SKU", e);
            throw new ErroInternoException("Erro ao buscar produto: " + e.getMessage());
        }
    }

    private ResponseDto montaResponse(ProdutoEntity produtoEntity) {
        ResponseDto response = new ResponseDto();
        response.setMessage(ConstantUtils.PRODUTO_CADASTRADO);

        Map<String, Object> data = new HashMap<>();
        data.put("skuProduto", produtoEntity.getSkuProduto());
        data.put("nomeProduto", produtoEntity.getNomeProduto());
        data.put("precoProduto", produtoEntity.getPrecoProduto());

        response.setData(data);
        return response;
    }

}
