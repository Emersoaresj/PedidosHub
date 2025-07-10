package com.fiap.postech.pedidohub.cliente.api.controller;

import com.fiap.postech.pedidohub.cliente.api.dto.ClienteDto;
import com.fiap.postech.pedidohub.cliente.api.dto.ClienteRequest;
import com.fiap.postech.pedidohub.cliente.gateway.port.ClienteServicePort;
import com.fiap.postech.pedidohub.commom.utils.ResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteServicePort service;

    @PostMapping
    public ResponseEntity<ResponseDto> cadastraCliente(@Valid @RequestBody ClienteRequest request) {
        ResponseDto cadastro = service.cadastraCliente(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(cadastro);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<ClienteDto> buscarClientePorCpf(@PathVariable("cpf") String cpf) {
        ClienteDto cliente = service.buscarClientePorCpf(cpf);
        return ResponseEntity.status(HttpStatus.OK).body(cliente);
    }
}

