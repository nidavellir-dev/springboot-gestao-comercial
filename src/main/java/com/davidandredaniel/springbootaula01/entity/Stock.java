package com.davidandredaniel.springbootaula01.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "estoque")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_Id", nullable = false, unique = true)
    private Produto produto;

    @NotNull(message = "A quantidade em estoque não pode ser nula")
    @PositiveOrZero(message = "A quantidade em estoque deve ser um valor positivo")
    private Integer quantidade_actual = 0;

    @NotNull(message = "A quantidade minima não pode ser nulo")
    @PositiveOrZero(message = "A quantidade minima deve ser um valor positivo")
    private Integer quantidade_minima;

    public Stock() {
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade_actual() {
        return quantidade_actual;
    }

    public void setQuantidade_actual(Integer quantidade_actual) {
        this.quantidade_actual = quantidade_actual;
    }

    public Integer getQuantidade_minima() {
        return quantidade_minima;
    }

    public void setQuantidade_minima(Integer quantidade_minima) {
        this.quantidade_minima = quantidade_minima;
    }
}
