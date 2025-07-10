package com.fiap.postech.pedidohub.cliente.api.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClienteDto {
    private Integer idCliente;
    private String nomeCliente;
    private String cpfCliente;
    private LocalDate dataNascimento;
    private String cidade;
    private String estado;
}
