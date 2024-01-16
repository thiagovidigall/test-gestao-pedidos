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