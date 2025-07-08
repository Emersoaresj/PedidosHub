package com.fiap.postech.pedidohub.estoque.service;

import com.fiap.postech.pedidohub.config.ErroInternoException;
import com.fiap.postech.pedidohub.estoque.domain.exceptions.InvalidQuantidadeEstoqueException;
import com.fiap.postech.pedidohub.estoque.domain.exceptions.InvalidSkuEstoqueException;
import com.fiap.postech.pedidohub.estoque.domain.model.Estoque;
import com.fiap.postech.pedidohub.estoque.domain.exceptions.EstoqueExistsException;
import com.fiap.postech.pedidohub.estoque.domain.exceptions.ProdutoNotFoundException;
import com.fiap.postech.pedidohub.estoque.api.dto.EstoqueRequest;
import com.fiap.postech.pedidohub.estoque.gateway.client.ProdutoClient;
import com.fiap.postech.pedidohub.estoque.gateway.port.EstoqueRepositoryPort;
import com.fiap.postech.pedidohub.estoque.gateway.port.EstoqueServicePort;
import com.fiap.postech.pedidohub.estoque.api.mapper.EstoqueMapper;
import com.fiap.postech.pedidohub.produto.api.dto.ProdutoDto;
import com.fiap.postech.pedidohub.utils.ConstantUtils;
import com.fiap.postech.pedidohub.utils.ResponseDto;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EstoqueServiceImpl implements EstoqueServicePort {

    @Autowired
    private EstoqueRepositoryPort repositoryPort;

    @Autowired
    private ProdutoClient produtoClient;

    @Override
    public ResponseDto cadastrarEstoque(EstoqueRequest request) {
        try {
            Estoque estoque = EstoqueMapper.INSTANCE.requestToDomain(request);

            validaEstoque(estoque);

            ProdutoDto produto = chamadaProdutoClient(estoque);
            estoque.setIdProduto(produto.getIdProduto());

            if (repositoryPort.existeEstoquePorIdProduto(estoque.getIdProduto())) {
                log.warn("Estoque já cadastrado para o SKU: {}", estoque.getSkuProduto());
                throw new EstoqueExistsException(ConstantUtils.ESTOQUE_JA_EXISTE);
            }
            return repositoryPort.cadastrarEstoque(estoque);

        } catch (InvalidQuantidadeEstoqueException | InvalidSkuEstoqueException |
                 ProdutoNotFoundException | EstoqueExistsException e) {
            throw e;
        } catch (Exception e) {
            log.error("Erro inesperado ao cadastrar estoque", e);
            throw new ErroInternoException("Erro interno ao tentar cadastrar estoque: " + e.getMessage());
        }
    }

    private void validaEstoque(Estoque estoque) {
        if (!estoque.quantidadeValida()) {
            log.warn("Quantidade inválida para o SKU: {}", estoque.getSkuProduto());
            throw new InvalidQuantidadeEstoqueException("Quantidade não pode ser negativa.");
        }
        if (!estoque.skuValido()) {
            log.warn("SKU inválido: {}", estoque.getSkuProduto());
            throw new InvalidSkuEstoqueException("SKU inválido. Deve seguir o padrão XX-XX-XXX.");
        }
    }


    private ProdutoDto chamadaProdutoClient(Estoque estoque) {
        try {
            return produtoClient.buscarPorSku(estoque.getSkuProduto());
        } catch (FeignException.NotFound e) {
            log.error("Produto não encontrado para o SKU: {}", estoque.getSkuProduto());
            throw new ProdutoNotFoundException(ConstantUtils.PRODUTO_NAO_ENCONTRADO);
        } catch (Exception e) {
            log.error("Erro ao chamar o serviço de produto: {}", e.getMessage());
            throw new ErroInternoException("Erro ao buscar produto: " + e.getMessage());
        }
    }

}
