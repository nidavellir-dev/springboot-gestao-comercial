CREATE TABLE compra (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        valor_total DECIMAL(15,2) NOT NULL,
                        data_compra DATETIME NOT NULL
);

CREATE TABLE item_compra (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             compra_id BIGINT NOT NULL,
                             produto_id BIGINT NOT NULL,
                             quantidade INT NOT NULL,
                             preco_unitario DECIMAL(15,2) NOT NULL,
                             subtotal DECIMAL(15,2) NOT NULL,

                             CONSTRAINT fk_item_compra_compra
                                 FOREIGN KEY (compra_id) REFERENCES compra(id),

                             CONSTRAINT fk_item_compra_produto
                                 FOREIGN KEY (produto_id) REFERENCES produto(id),

                             CONSTRAINT chk_item_compra_quantidade
                                 CHECK (quantidade > 0),

                             CONSTRAINT chk_item_compra_preco
                                 CHECK (preco_unitario > 0),

                             CONSTRAINT chk_item_compra_subtotal
                                 CHECK (subtotal > 0)
);