package com.davidandredaniel.springbootaula01.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class CompraRequestDTO {

    @NotEmpty(message = "A compra deve possuir pelo menos um item")
    @Valid
    private List<ItemCompraRequestDTO> itens;

    public CompraRequestDTO() {
    }

    public List<ItemCompraRequestDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemCompraRequestDTO> itens) {
        this.itens = itens;
    }
}