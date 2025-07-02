package com.fiap.postech.pedidoHub.cliente.controller;

import com.fiap.postech.pedidoHub.cliente.dto.ClienteDto;
import com.fiap.postech.pedidoHub.cliente.dto.ClienteRequest;
import com.fiap.postech.pedidoHub.cliente.service.port.ClienteServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteServicePort service;

    @PostMapping
    public ResponseEntity<ClienteDto> salvar(@RequestBody ClienteRequest request) {
        ClienteDto cadastro = service.cadastraCliente(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(cadastro);
    }
}

