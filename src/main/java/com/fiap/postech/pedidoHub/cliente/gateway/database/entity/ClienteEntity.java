package com.fiap.postech.pedidoHub.cliente.gateway.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "cliente")
@Entity
public class ClienteEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_cliente")
        private Integer idCliente;

        @Column(name = "nome_cliente")
        private String nomeCliente;

        @Column(name = "cpf_cliente")
        private String cpfCliente;

        @Column(name = "data_nascimento")
        private LocalDate dataNascimento;

        @Column(name = "logradouro")
        private String logradouro;

        @Column(name = "numero_endereco")
        private String numeroEndereco;

        @Column(name = "cep")
        private String cep;

        @Column(name = "complemento_endereco")
        private String complementoEndereco;

        @Column(name = "bairro")
        private String bairro;

        @Column(name = "cidade")
        private String cidade;

        @Column(name = "estado")
        private String estado;


}
