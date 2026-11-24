
CREATE TABLE movimento_estoque (
                                   id BIGINT AUTO_INCREMENT PRIMARY KEY,

                                   estoque_id BIGINT NOT NULL,

                                   tipo VARCHAR(20) NOT NULL,

                                   quantidade INT NOT NULL,

                                   motivo VARCHAR(255) NOT NULL,

                                   data_movimento DATETIME NOT NULL,

                                   CONSTRAINT fk_movimento_estoque_stock
                                       FOREIGN KEY (estoque_id)
                                           REFERENCES estoque(id),

                                   CONSTRAINT chk_movimento_quantidade
                                       CHECK (quantidade > 0),

                                   CONSTRAINT chk_movimento_tipo
                                       CHECK (tipo IN ('ENTRADA', 'SAIDA', 'AJUSTE'))
);
