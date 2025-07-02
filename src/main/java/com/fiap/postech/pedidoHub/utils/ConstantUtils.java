package com.fiap.postech.pedidoHub.utils;

import lombok.Data;

import java.util.Set;

@Data
public class ConstantUtils {




    private ConstantUtils() {
        throw new IllegalStateException("Classe Utilitária");
    }


    //ERROS
    public static final String CLIENTE_JA_EXISTE = "O cliente já está cadastrado!";
    public static final String CPF_INVALIDO = "CPF inválido! O formato deve ser XXX.XXX.XXX-XX";
    public static final String DATA_NASCIMENTO_INVALIDA = "Data de nascimento inválida! A data não pode ser futura.";

    //SUCESSO
    public static final String CLIENTE_CADASTRADO = "Usuário cadastrado com sucesso!";

}
