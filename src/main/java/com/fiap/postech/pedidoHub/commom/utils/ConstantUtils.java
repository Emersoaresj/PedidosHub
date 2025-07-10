package com.fiap.postech.pedidohub.commom.utils;

import lombok.Data;

@Data
public class ConstantUtils {




    private ConstantUtils() {
        throw new IllegalStateException("Classe Utilitária");
    }


    //ERROS
    public static final String CLIENTE_JA_EXISTE = "O cliente já está cadastrado!";
    public static final String CLIENTE_NAO_ENCONTRADO = "Cliente não encontrado para o CPF informado.";
    public static final String CPF_INVALIDO = "CPF inválido! O formato deve ser XXX.XXX.XXX-XX";
    public static final String DATA_NASCIMENTO_INVALIDA = "Data de nascimento inválida! A data não pode ser futura.";
    public static final String CEP_INVALIDO = "CEP inválido! O formato deve ser XXXXX-XXX";
    public static final String ESTADO_INVALIDO = "Estado inválido! O formato deve ser XX (sigla do estado)";

    public static final String PRODUTO_JA_EXISTE = "O produto já está cadastrado!";
    public static final String PRECO_INVALIDO = "Preço inválido! O preço deve ser maior que zero.";
    public static final String SKU_INVALIDO = "SKU inválido. O SKU deve ser alfanumérico e com hífens entre as palavras. Exemplo válido: NB-DEL-001";
    public static final String PRODUTO_NAO_ENCONTRADO = "Produto não encontrado para o SKU informado.";

    public static final String ESTOQUE_JA_EXISTE = "Já existe estoque cadastrado para o SKU informado.";

    public static final String PEDIDO_NAO_PODE_SER_NULO = "O pedido não pode ser nulo!";
    public static final String ITENS_PEDIDO_INVALIDOS = "Itens do pedido inválidos! Verifique se todos os itens possuem SKU e quantidade válidos.";
    public static final String VALOR_TOTAL_PEDIDO_INVALIDO = "Valor total do pedido inválido! O valor total deve ser maior que zero.";

    //SUCESSO
    public static final String CLIENTE_CADASTRADO = "Usuário cadastrado com sucesso!";
    public static final String PRODUTO_CADASTRADO = "Produto cadastrado com sucesso!";
    public static final String ESTOQUE_CADASTRADO = "Estoque cadastrado com sucesso!";
    public static final String PEDIDO_CADASTRADO = "Pedido cadastrado com sucesso!";
}
