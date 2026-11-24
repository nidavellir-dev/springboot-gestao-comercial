
CREATE TABLE estoque (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,

                         produto_id BIGINT NOT NULL UNIQUE,

                         quantidade_actual INT NOT NULL DEFAULT 0,

                         quantidade_minima INT NOT NULL DEFAULT 0,

                         CONSTRAINT fk_estoque_produto
                             FOREIGN KEY (produto_id)
                                 REFERENCES produto(id),

                         CONSTRAINT chk_estoque_quantidade_actual
                             CHECK (quantidade_actual >= 0),

                         CONSTRAINT chk_estoque_quantidade_minima
                             CHECK (quantidade_minima >= 0)
);
