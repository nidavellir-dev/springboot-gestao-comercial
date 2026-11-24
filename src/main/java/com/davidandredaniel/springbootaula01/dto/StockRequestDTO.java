
package com.davidandredaniel.springbootaula01.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class StockRequestDTO {

    @NotNull(message = "O ID do produto é obrigatório")
    private Long produtoId;

    @NotNull(message = "A quantidade mínima é obrigatória")
    @PositiveOrZero(message = "A quantidade mínima não pode ser negativa")
    private Integer quantidadeMinima;

    public StockRequestDTO() {
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getQuantidadeMinima() {
        return quantidadeMinima;
    }

    public void setQuantidadeMinima(Integer quantidadeMinima) {
        this.quantidadeMinima = quantidadeMinima;
    }
}
