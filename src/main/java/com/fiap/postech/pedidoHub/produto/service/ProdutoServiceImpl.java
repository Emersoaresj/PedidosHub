package com.fiap.postech.pedidohub.produto.service;

import com.fiap.postech.pedidohub.utils.ResponseDto;
import com.fiap.postech.pedidohub.config.ErroInternoException;
import com.fiap.postech.pedidohub.produto.api.mapper.ProdutoMapper;
import com.fiap.postech.pedidohub.produto.domain.exceptions.InvalidPrecoException;
import com.fiap.postech.pedidohub.produto.domain.exceptions.InvalidSkuException;
import com.fiap.postech.pedidohub.produto.domain.exceptions.ProdutoExistsException;
import com.fiap.postech.pedidohub.produto.domain.model.Produto;
import com.fiap.postech.pedidohub.produto.api.dto.ProdutoRequest;
import com.fiap.postech.pedidohub.produto.gateway.port.ProdutoRepositoryPort;
import com.fiap.postech.pedidohub.produto.gateway.port.ProdutoServicePort;
import com.fiap.postech.pedidohub.utils.ConstantUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProdutoServiceImpl implements ProdutoServicePort {

    @Autowired
    private ProdutoRepositoryPort repositoryPort;

    @Override
    public ResponseDto cadastrarProduto(ProdutoRequest request) {

        try{
            Produto produto = ProdutoMapper.INSTANCE.requestToDomain(request);

            validaProduto(produto);

            return repositoryPort.cadastrarProduto(produto);
        } catch (InvalidPrecoException | InvalidSkuException | ProdutoExistsException e) {
            throw e;
        } catch (Exception e) {
            log.error("Erro inesperado ao cadastrar produto", e);
            throw new ErroInternoException("Erro interno ao tentar cadastrar produto: " + e.getMessage());
        }


    }

    private void validaProduto(Produto produto) {
        if (!produto.precoValido()) {
            log.warn("Preço inválido para produto SKU {}: {}", produto.getSkuProduto(), produto.getPrecoProduto());
            throw new InvalidPrecoException(ConstantUtils.PRECO_INVALIDO);
        }

        if (!produto.skuValido()) {
            log.warn("SKU inválido: {}", produto.getSkuProduto());
            throw new InvalidSkuException(ConstantUtils.SKU_INVALIDO);
        }

        if (repositoryPort.findBySkuProduto(produto.getSkuProduto()).isPresent()) {
            log.warn("Produto já cadastrado com SKU: {}", produto.getSkuProduto());
            throw new ProdutoExistsException(ConstantUtils.PRODUTO_JA_EXISTE);
        }
    }
}
