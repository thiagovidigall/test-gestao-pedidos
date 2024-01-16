CREATE DATABASE gestao_pedidos_db CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

CREATE TABLE clientes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE pedidos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    numero_controle BIGINT NOT NULL,
    nome_produto VARCHAR(100) NOT NULL,
    quantidade INTEGER NOT NULL DEFAULT 1,
    valor DECIMAL(10,2) NOT NULL,
    valor_total DECIMAL(10,2) NOT NULL,
    data_cadastro Date NOT NULL,
    fk_cliente_id BIGINT NOT NULL,
    FOREIGN KEY (fk_cliente_id) REFERENCES clientes(id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

INSERT INTO clientes (nome) values ('Cliente 1');
INSERT INTO clientes (nome) values ('Cliente 2');
INSERT INTO clientes (nome) values ('Cliente 3');
INSERT INTO clientes (nome) values ('Cliente 4');
INSERT INTO clientes (nome) values ('Cliente 5');
INSERT INTO clientes (nome) values ('Cliente 6');
INSERT INTO clientes (nome) values ('Cliente 7');
INSERT INTO clientes (nome) values ('Cliente 8');
INSERT INTO clientes (nome) values ('Cliente 9');
INSERT INTO clientes (nome) values ('Cliente 10');

