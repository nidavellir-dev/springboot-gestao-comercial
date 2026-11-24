
package com.davidandredaniel.springbootaula01.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ItemVendaRequestDTO {

    @NotNull(message = "O ID do produto é obrigatório")
    @Positive(message = "O ID do produto deve ser positivo")
    private Long produtoId;

    @NotNull(message = "A quantidade é obrigatória")
    @Positive(message = "A quantidade deve ser superior a zero")
    private Integer quantidade;

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
