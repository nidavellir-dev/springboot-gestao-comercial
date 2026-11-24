
package com.davidandredaniel.springbootaula01.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class VendaResponseDTO {

    private Long id;
    private LocalDateTime dataVenda;
    private BigDecimal valorTotal;
    private List<ItemVendaResponseDTO> itens;

    public VendaResponseDTO(
            Long id,
            LocalDateTime dataVenda,
            BigDecimal valorTotal,
            List<ItemVendaResponseDTO> itens) {

        this.id = id;
        this.dataVenda = dataVenda;
        this.valorTotal = valorTotal;
        this.itens = itens;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDataVenda() {
        return dataVenda;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public List<ItemVendaResponseDTO> getItens() {
        return itens;
    }
}
