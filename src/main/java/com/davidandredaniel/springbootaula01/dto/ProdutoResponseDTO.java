
package com.davidandredaniel.springbootaula01.dto;

public class ProdutoResponseDTO {

    private Long id;
    private String nome;
    private Double preco;
    private CategoriaResponseDTO categoria;

    public ProdutoResponseDTO() {
    }

    public ProdutoResponseDTO(
            Long id,
            String nome,
            Double preco,
            CategoriaResponseDTO categoria) {

        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public CategoriaResponseDTO getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaResponseDTO categoria) {
        this.categoria = categoria;
    }
}
