
package com.davidandredaniel.springbootaula01.dto;

import com.davidandredaniel.springbootaula01.entity.TipoMovimento;

import java.time.LocalDateTime;

public class MovimentoEstoqueResponseDTO {

    private Long id;
    private Long produtoId;
    private String nomeProduto;
    private TipoMovimento tipo;
    private Integer quantidade;
    private String motivo;
    private LocalDateTime dataMovimento;

    public MovimentoEstoqueResponseDTO() {
    }

    public MovimentoEstoqueResponseDTO(
            Long id,
            Long produtoId,
            String nomeProduto,
            TipoMovimento tipo,
            Integer quantidade,
            String motivo,
            LocalDateTime dataMovimento) {

        this.id = id;
        this.produtoId = produtoId;
        this.nomeProduto = nomeProduto;
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.motivo = motivo;
        this.dataMovimento = dataMovimento;
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

    public TipoMovimento getTipo() {
        return tipo;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public String getMotivo() {
        return motivo;
    }

    public LocalDateTime getDataMovimento() {
        return dataMovimento;
    }
}
