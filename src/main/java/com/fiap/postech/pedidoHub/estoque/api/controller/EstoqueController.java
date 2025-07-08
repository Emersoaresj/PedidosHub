package com.fiap.postech.pedidohub.estoque.api.controller;

import com.fiap.postech.pedidohub.estoque.api.dto.EstoqueRequest;
import com.fiap.postech.pedidohub.estoque.gateway.port.EstoqueServicePort;
import com.fiap.postech.pedidohub.utils.ResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/estoques")
public class EstoqueController {

    @Autowired
    private EstoqueServicePort service;


    @PostMapping
    public ResponseEntity<ResponseDto> cadastrarEstoque(@Valid @RequestBody EstoqueRequest request) {
        ResponseDto estoque = service.cadastrarEstoque(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(estoque);
    }

    // Exemplos de endpoints que podem ser úteis:
    // @GetMapping("/{sku}")
    // public ResponseEntity<EstoqueDto> consultarPorSku(@PathVariable String sku) { ... }
    //
    // @PutMapping("/{sku}")
    // public ResponseEntity<EstoqueDto> atualizarQuantidade(@PathVariable String sku, @RequestBody AtualizaEstoqueRequest req) { ... }
}
