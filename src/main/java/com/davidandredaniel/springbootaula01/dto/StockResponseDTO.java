
package com.davidandredaniel.springbootaula01.dto;

public class StockResponseDTO {

    private Long id;
    private Long produtoId;
    private String nomeProduto;
    private Integer quantidadeActual;
    private Integer quantidadeMinima;

    public StockResponseDTO() {
    }

    public StockResponseDTO(
            Long id,
            Long produtoId,
            String nomeProduto,
            Integer quantidadeActual,
            Integer quantidadeMinima) {

        this.id = id;
        this.produtoId = produtoId;
        this.nomeProduto = nomeProduto;
        this.quantidadeActual = quantidadeActual;
        this.quantidadeMinima = quantidadeMinima;
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

    public Integer getQuantidadeActual() {
        return quantidadeActual;
    }

    public Integer getQuantidadeMinima() {
        return quantidadeMinima;
    }
}
