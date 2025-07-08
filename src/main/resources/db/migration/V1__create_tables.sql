-- Tipos ENUM
CREATE TYPE pedido_status AS ENUM (
    'ABERTO',
    'FECHADO_COM_SUCESSO',
    'FECHADO_SEM_ESTOQUE',
    'FECHADO_SEM_CREDITO'
);

CREATE TYPE pagamento_status AS ENUM (
    'PENDENTE',
    'APROVADO',
    'ESTORNADO',
    'RECUSADO'
);

-- Tabela cliente
CREATE TABLE cliente (
    id_cliente SERIAL PRIMARY KEY,
    nome_cliente VARCHAR(150) NOT NULL,
    cpf_cliente VARCHAR(14) NOT NULL UNIQUE,
    data_nascimento DATE NOT NULL,
    logradouro VARCHAR(255) NOT NULL,
    numero_endereco VARCHAR(20) NOT NULL,
    cep VARCHAR(10) NOT NULL,
    complemento_endereco VARCHAR(255),
    bairro VARCHAR(100),
    cidade VARCHAR(100),
    estado VARCHAR(2)
);

-- Tabela produto
CREATE TABLE produto (
    id_produto SERIAL PRIMARY KEY,
    nome_produto VARCHAR(150) NOT NULL,
    sku_produto VARCHAR(50) NOT NULL UNIQUE,
    preco_produto DECIMAL(10,2) NOT NULL
);

-- Tabela estoque
CREATE TABLE estoque (
    id_estoque SERIAL PRIMARY KEY,
    id_produto INTEGER NOT NULL UNIQUE,
    quantidade_estoque INTEGER NOT NULL,
    sku_produto VARCHAR(50) NOT NULL UNIQUE,
    FOREIGN KEY (id_produto) REFERENCES produto(id_produto)
);

-- Tabela pedido
CREATE TABLE pedido (
    id_pedido SERIAL PRIMARY KEY,
    id_cliente INTEGER NOT NULL,
    status_pedido pedido_status NOT NULL DEFAULT 'ABERTO',
    data_pedido TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    valor_total_pedido DECIMAL(12,2) NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente)
);

-- Tabela pedido_item
CREATE TABLE pedido_item (
    id_pedido_item SERIAL PRIMARY KEY,
    id_pedido INTEGER NOT NULL,
    id_produto INTEGER NOT NULL,
    quantidade_item INTEGER NOT NULL,
    preco_unitario_item DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido),
    FOREIGN KEY (id_produto) REFERENCES produto(id_produto)
);

-- Tabela pagamento
CREATE TABLE pagamento (
    id_pagamento SERIAL PRIMARY KEY,
    id_pedido INTEGER NOT NULL,
    numero_cartao VARCHAR(20) NOT NULL,
    status_pagamento pagamento_status NOT NULL DEFAULT 'PENDENTE',
    data_pagamento TIMESTAMP,
    valor_pago DECIMAL(12,2) NOT NULL,
    FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido)
);
