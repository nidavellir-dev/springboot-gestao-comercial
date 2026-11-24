package com.davidandredaniel.springbootaula01.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class ItemCompraRequestDTO {

    @NotNull(message = "O ID do produto é obrigatório")
    @Positive(message = "O ID do produto deve ser positivo")
    private Long produtoId;

    @NotNull(message = "A quantidade é obrigatória")
    @Positive(message = "A quantidade deve ser superior a zero")
    private Integer quantidade;

    @NotNull(message = "O preço unitário é obrigatório")
    @Positive(message = "O preço unitário deve ser superior a zero")
    private BigDecimal precoUnitario;

    public ItemCompraRequestDTO() {
    }

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

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
}