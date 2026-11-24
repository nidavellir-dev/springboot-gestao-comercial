package com.davidandredaniel.springbootaula01.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoriaRequestDTO {
    @NotBlank(message = " O nome da categoria é obrigatório")
    private String nome;

    public CategoriaRequestDTO() {
    }

    public CategoriaRequestDTO(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
