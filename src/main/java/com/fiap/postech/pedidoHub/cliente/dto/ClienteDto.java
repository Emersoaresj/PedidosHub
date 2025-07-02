package com.fiap.postech.pedidoHub.cliente.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClienteDto {

    private String mensagem;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
}
