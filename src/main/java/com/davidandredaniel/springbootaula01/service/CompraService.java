package com.davidandredaniel.springbootaula01.service;

import com.davidandredaniel.springbootaula01.dto.*;
import com.davidandredaniel.springbootaula01.entity.*;
import com.davidandredaniel.springbootaula01.repository.*;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CompraService {

    private final CompraRepository compraRepository;
    private final ItemCompraRepository itemCompraRepository;
    private final ProdutoRepository produtoRepository;
    private final StockRepository stockRepository;
    private final StockService stockService;

    public CompraService(
            CompraRepository compraRepository,
            ItemCompraRepository itemCompraRepository,
            ProdutoRepository produtoRepository,
            StockRepository stockRepository,
            StockService stockService
    ) {
        this.compraRepository = compraRepository;
        this.itemCompraRepository = itemCompraRepository;
        this.produtoRepository = produtoRepository;
        this.stockRepository = stockRepository;
        this.stockService = stockService;
    }

    @Transactional
    public CompraResponseDTO registarCompra(CompraRequestDTO dto) {

        if (dto.getItens() == null || dto.getItens().isEmpty()) {
            throw new IllegalArgumentException(
                    "A compra deve possuir pelo menos um item"
            );
        }

        Compra compra = new Compra();
        compra.setDataCompra(LocalDateTime.now());
        compra.setValorTotal(BigDecimal.ZERO);

        compra = compraRepository.save(compra);

        BigDecimal valorTotal = BigDecimal.ZERO;

        Set<Long> produtosRegistados = new HashSet<>();

        for (ItemCompraRequestDTO itemDTO : dto.getItens()) {

            if (itemDTO.getProdutoId() == null ||
                    itemDTO.getQuantidade() == null ||
                    itemDTO.getPrecoUnitario() == null) {

                throw new IllegalArgumentException(
                        "Os dados de cada item da compra são obrigatórios"
                );
            }

            if (itemDTO.getQuantidade() <= 0) {
                throw new IllegalArgumentException(
                        "A quantidade deve ser superior a zero"
                );
            }

            if (itemDTO.getPrecoUnitario().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException(
                        "O preço unitário deve ser superior a zero"
                );
            }

            if (!produtosRegistados.add(itemDTO.getProdutoId())) {
                throw new IllegalArgumentException(
                        "Não é permitido repetir o mesmo produto na compra"
                );
            }

            Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Produto não encontrado: " + itemDTO.getProdutoId()
                    ));

            garantirStockExistente(produto);

            BigDecimal subtotal = itemDTO.getPrecoUnitario()
                    .multiply(BigDecimal.valueOf(itemDTO.getQuantidade()));

            ItemCompra item = new ItemCompra();
            item.setCompra(compra);
            item.setProduto(produto);
            item.setQuantidade(itemDTO.getQuantidade());
            item.setPrecoUnitario(itemDTO.getPrecoUnitario());
            item.setSubtotal(subtotal);

            itemCompraRepository.save(item);

            compra.getItens().add(item);

            valorTotal = valorTotal.add(subtotal);

            stockService.registrarEntrada(
                    produto.getId(),
                    itemDTO.getQuantidade(),
                    "Entrada referente à compra"
            );
        }

        compra.setValorTotal(valorTotal);

        compraRepository.save(compra);

        return converterCompra(compra);
    }

    @Transactional(readOnly = true)
    public CompraResponseDTO consultarCompra(Long id) {

        Compra compra = compraRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Compra não encontrada: " + id
                ));

        return converterCompra(compra);
    }

    @Transactional(readOnly = true)
    public List<CompraResponseDTO> listarCompras() {

        return compraRepository.findAll()
                .stream()
                .map(this::converterCompra)
                .toList();
    }

    private void garantirStockExistente(Produto produto) {

        boolean existeStock = stockRepository.existsByProdutoId(produto.getId());

        if (!existeStock) {

            Stock stock = new Stock();
            stock.setProduto(produto);
            stock.setQuantidade_actual(0);
            stock.setQuantidade_minima(0);

            stockRepository.save(stock);
        }
    }

    private CompraResponseDTO converterCompra(Compra compra) {

        List<ItemCompraResponseDTO> itens = new ArrayList<>();

        for (ItemCompra item : compra.getItens()) {

            itens.add(new ItemCompraResponseDTO(
                    item.getId(),
                    item.getProduto().getId(),
                    item.getProduto().getNome(),
                    item.getQuantidade(),
                    item.getPrecoUnitario(),
                    item.getSubtotal()
            ));
        }

        return new CompraResponseDTO(
                compra.getId(),
                compra.getValorTotal(),
                compra.getDataCompra(),
                itens
        );
    }
}