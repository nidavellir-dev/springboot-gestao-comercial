
package com.davidandredaniel.springbootaula01.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class VendaRequestDTO {

    @NotEmpty(message = "A venda deve conter pelo menos um produto")
    @Valid
    private List<ItemVendaRequestDTO> itens;

    public List<ItemVendaRequestDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemVendaRequestDTO> itens) {
        this.itens = itens;
    }
}
