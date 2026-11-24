package com.davidandredaniel.springbootaula01.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class CompraResponseDTO {

    private Long id;
    private BigDecimal valorTotal;
    private LocalDateTime dataCompra;
    private List<ItemCompraResponseDTO> itens;

    public CompraResponseDTO(
            Long id,
            BigDecimal valorTotal,
            LocalDateTime dataCompra,
            List<ItemCompraResponseDTO> itens
    ) {
        this.id = id;
        this.valorTotal = valorTotal;
        this.dataCompra = dataCompra;
        this.itens = itens;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public LocalDateTime getDataCompra() {
        return dataCompra;
    }

    public List<ItemCompraResponseDTO> getItens() {
        return itens;
    }
}