package com.fiap.postech.pedidohub.produto.service;

import com.fiap.postech.pedidohub.produto.api.dto.ProdutoDto;
import com.fiap.postech.pedidohub.produto.domain.exceptions.ProdutoNotFoundException;
import com.fiap.postech.pedidohub.commom.utils.ResponseDto;
import com.fiap.postech.pedidohub.commom.config.ErroInternoException;
import com.fiap.postech.pedidohub.produto.api.mapper.ProdutoMapper;
import com.fiap.postech.pedidohub.produto.domain.exceptions.InvalidPrecoException;
import com.fiap.postech.pedidohub.produto.domain.exceptions.InvalidSkuException;
import com.fiap.postech.pedidohub.produto.domain.exceptions.ProdutoExistsException;
import com.fiap.postech.pedidohub.produto.domain.model.Produto;
import com.fiap.postech.pedidohub.produto.api.dto.ProdutoRequest;
import com.fiap.postech.pedidohub.produto.gateway.port.ProdutoRepositoryPort;
import com.fiap.postech.pedidohub.produto.gateway.port.ProdutoServicePort;
import com.fiap.postech.pedidohub.commom.utils.ConstantUtils;
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

        try {
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

    @Override
    public ProdutoDto buscarPorSku(String sku) {
        try {
            Produto produto = repositoryPort.findBySkuProduto(sku)
                    .orElseThrow(() -> new ProdutoNotFoundException(ConstantUtils.PRODUTO_NAO_ENCONTRADO));
            return ProdutoMapper.INSTANCE.domainToDtoClient(produto);
        } catch (ProdutoNotFoundException e) {
            log.error("Produto não encontrado para o SKU: {}", sku, e);
            throw e;
        } catch (Exception e) {
            log.error("Erro inesperado ao buscar produto por SKU: {}", sku, e);
            throw new ErroInternoException("Erro interno ao tentar buscar produto: " + e.getMessage());
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
