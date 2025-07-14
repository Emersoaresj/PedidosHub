package com.fiap.postech.pedidohub.pedido.api.controller;

import com.fiap.postech.pedidohub.pedido.api.dto.AtualizarPedidoRequest;
import com.fiap.postech.pedidohub.pedido.api.dto.PedidoRequest;
import com.fiap.postech.pedidohub.pedido.gateway.port.PedidoServicePort;
import com.fiap.postech.pedidohub.commom.utils.ResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoServicePort service;

    @PostMapping
    public ResponseEntity<ResponseDto> cadastrarPedido(@Valid @RequestBody PedidoRequest request) {
        ResponseDto response = service.cadastrarPedido(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> atualizarPedido(@PathVariable("id") Integer id, @RequestBody AtualizarPedidoRequest request) {
        ResponseDto response = service.atualizarPedido(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}