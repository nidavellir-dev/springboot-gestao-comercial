package com.davidandredaniel.springbootaula01.dto;

import java.math.BigDecimal;

public class ItemCompraResponseDTO {

    private Long id;
    private Long produtoId;
    private String nomeProduto;
    private Integer quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal subtotal;

    public ItemCompraResponseDTO(
            Long id,
            Long produtoId,
            String nomeProduto,
            Integer quantidade,
            BigDecimal precoUnitario,
            BigDecimal subtotal
    ) {
        this.id = id;
        this.produtoId = produtoId;
        this.nomeProduto = nomeProduto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.subtotal = subtotal;
    }

    public Long getId() {
        return id;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }
}