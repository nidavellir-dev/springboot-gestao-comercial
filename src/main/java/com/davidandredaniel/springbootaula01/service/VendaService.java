package com.davidandredaniel.springbootaula01.service;

import com.davidandredaniel.springbootaula01.dto.*;
import com.davidandredaniel.springbootaula01.entity.*;
import com.davidandredaniel.springbootaula01.exception.VendaNaoEncontradaException;
import com.davidandredaniel.springbootaula01.repository.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;
    private final StockService stockService;

    public VendaService(
            VendaRepository vendaRepository,
            ProdutoRepository produtoRepository,
            StockService stockService
    ) {
        this.vendaRepository = vendaRepository;
        this.produtoRepository = produtoRepository;
        this.stockService = stockService;
    }

    // REGISTAR VENDA
    @Transactional
    public VendaResponseDTO criarVenda(VendaRequestDTO dto) {

        Venda venda = new Venda();

        venda.setDataVenda(LocalDateTime.now());

        BigDecimal valorTotal = BigDecimal.ZERO;

        // CONSTRUIR OS ITENS DA VENDA
        for (ItemVendaRequestDTO itemDTO : dto.getItens()) {

            Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                    .orElseThrow(() ->
                            new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    "Produto não encontrado: " + itemDTO.getProdutoId()
                            )
                    );

            if (produto.getPreco() == null) {
                throw new VendaNaoEncontradaException(
                        "O produto não possui preço definido: " + produto.getId()
                );
            }

            BigDecimal precoUnitario = BigDecimal.valueOf(produto.getPreco());

            BigDecimal subtotal = precoUnitario.multiply(
                    BigDecimal.valueOf(itemDTO.getQuantidade())
            );

            ItemVenda item = new ItemVenda();

            item.setProduto(produto);
            item.setQuantidade(itemDTO.getQuantidade());
            item.setPrecoUnitario(precoUnitario);
            item.setSubtotal(subtotal);

            venda.adicionarItem(item);

            valorTotal = valorTotal.add(subtotal);
        }

        // CALCULAR O VALOR TOTAL
        venda.setValorTotal(valorTotal);

        // GUARDAR A VENDA
        Venda vendaSalva = vendaRepository.save(venda);

        // REGISTAR AS SAÍDAS NO STOCK
        for (ItemVenda item : vendaSalva.getItens()) {

            stockService.registrarSaida(
                    item.getProduto().getId(),
                    item.getQuantidade(),
                    "Venda #" + vendaSalva.getId()
            );
        }

        // DEVOLVER A VENDA EM DTO
        return converterParaDTO(vendaSalva);
    }


    // LISTAR TODAS AS VENDAS
    @Transactional(readOnly = true)
    public List<VendaResponseDTO> listarVendas() {

        return vendaRepository
                .findAllByOrderByDataVendaDesc()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }


    // BUSCAR VENDA POR ID
    @Transactional(readOnly = true)
    public VendaResponseDTO buscarVendaPorId(Long id) {

        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Venda não encontrada: " + id
                        )
                );

        return converterParaDTO(venda);
    }


    // CONVERTER ENTIDADE PARA DTO
    private VendaResponseDTO converterParaDTO(Venda venda) {

        List<ItemVendaResponseDTO> itensDTO = venda.getItens()
                .stream()
                .map(item -> new ItemVendaResponseDTO(
                        item.getProduto().getId(),
                        item.getProduto().getNome(),
                        item.getQuantidade(),
                        item.getPrecoUnitario(),
                        item.getSubtotal()
                ))
                .toList();

        return new VendaResponseDTO(
                venda.getId(),
                venda.getDataVenda(),
                venda.getValorTotal(),
                itensDTO
        );
    }
}