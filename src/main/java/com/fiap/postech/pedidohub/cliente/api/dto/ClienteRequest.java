package com.fiap.postech.pedidohub.cliente.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ClienteRequest {

    @NotBlank(message = "Nome do cliente é obrigatório")
    private String nomeCliente;
    @NotBlank(message = "CPF do cliente é obrigatório")
    private String cpfCliente;
    @NotNull(message = "Data de nascimento é obrigatória")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Data de nascimento deve estar no passado")
    private LocalDate dataNascimento;
    @NotBlank(message = "Logradouro é obrigatório")
    private String logradouro;
    @NotBlank(message = "Número do endereço é obrigatório")
    private String numeroEndereco;
    @NotBlank(message = "CEP é obrigatório")
    private String cep;
    private String complementoEndereco;
    @NotBlank(message = "Bairro é obrigatório")
    private String bairro;
    @NotBlank(message = "Cidade é obrigatória")
    private String cidade;
    @NotBlank(message = "Estado é obrigatório")

    private String estado;
}
