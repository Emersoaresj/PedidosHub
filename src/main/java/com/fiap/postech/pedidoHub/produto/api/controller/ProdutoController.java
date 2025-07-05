package com.fiap.postech.pedidohub.produto.api.controller;

import com.fiap.postech.pedidohub.utils.ResponseDto;
import com.fiap.postech.pedidohub.produto.api.dto.ProdutoRequest;
import com.fiap.postech.pedidohub.produto.gateway.port.ProdutoServicePort;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoServicePort service;

    @PostMapping
    public ResponseEntity<ResponseDto> salvar(@Valid @RequestBody ProdutoRequest request) {
        ResponseDto produto = service.cadastrarProduto(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(produto);
    }

    // @GetMapping("/{sku}") para consulta por SKU
}
