package com.fiap.postech.pedidoHub.cliente.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ClienteRequest {

    private String nomeCliente;
    private String cpfCliente;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;
    private String logradouro;
    private String numeroEndereco;
    private String cep;
    private String complementoEndereco;
    private String bairro;
    private String cidade;
    private String estado;
}
