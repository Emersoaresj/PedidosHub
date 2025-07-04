package com.fiap.postech.pedidohub.cliente.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cliente {

    private String nomeCliente;
    private String cpfCliente;
    private LocalDate dataNascimento;
    private String logradouro;
    private String numeroEndereco;
    private String cep;
    private String complementoEndereco;
    private String bairro;
    private String cidade;
    private String estado;

    public boolean cpfFormatoValido() {
        return cpfCliente != null && cpfCliente.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}");
    }

    public boolean nascimentoNaoEhFuturo() {
        return dataNascimento != null && !dataNascimento.isAfter(LocalDate.now());
    }

    public boolean cepFormatoValido() {
        return cep != null && cep.matches("\\d{5}-\\d{3}");
    }

    public boolean estadoFormatoValido() {
        return estado != null && estado.matches("[A-Z]{2}");
    }
}
