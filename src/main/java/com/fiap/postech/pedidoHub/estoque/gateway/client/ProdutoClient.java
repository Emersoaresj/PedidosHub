package com.fiap.postech.pedidohub.estoque.gateway.client;

import com.fiap.postech.pedidohub.produto.api.dto.ProdutoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "produto-service", url = "${produto.service.url}")
public interface ProdutoClient {

    @GetMapping("/api/produtos/sku/{sku}")
    ProdutoDto buscarPorSku(@PathVariable("sku") String sku);
}
