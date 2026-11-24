package com.davidandredaniel.springbootaula01.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "O nome do produto não pode estar em branco")
    private String nome;

    @NotNull(message = "O preço do produto não pode ser nulo")
    @Positive(message = "O preço do produto deve ser um valor positivo")
    private Double preco;

    @ManyToOne
    @JoinColumn (name = "categoria_id")
    private  Categoria categoria;


    public Produto() {
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}


//O que vamos aprender
//Nesta aula vamos aprender:
//Validação
//- @Valid
//- @NotBlank
//- @NotNull
//- @Positive
//- @PositiveOrZero
//- @Size
//Tratamento de erros
//- MethodArgumentNotValidException
//- @ExceptionHandler
//- @RestControllerAdvice
//- 400 Bad Request
//- 404 Not Found