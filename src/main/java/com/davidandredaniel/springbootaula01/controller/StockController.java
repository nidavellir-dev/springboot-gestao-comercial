
package com.davidandredaniel.springbootaula01.controller;

import com.davidandredaniel.springbootaula01.dto.StockRequestDTO;
import com.davidandredaniel.springbootaula01.dto.StockResponseDTO;
import com.davidandredaniel.springbootaula01.dto.MovimentoEstoqueResponseDTO;

import com.davidandredaniel.springbootaula01.service.StockService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estoque")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    // Criar stock para um produto
    @PostMapping
    public ResponseEntity<StockResponseDTO> criarStock(
            @Valid @RequestBody StockRequestDTO request) {

        StockResponseDTO resposta = stockService.criarStock(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    // Consultar stock de um produto
    @GetMapping("/{produtoId}")
    public ResponseEntity<StockResponseDTO> consultarStock(
            @PathVariable Long produtoId) {

        return ResponseEntity.ok(
                stockService.consultarStock(produtoId));
    }

    // Listar todos os stocks
    @GetMapping
    public ResponseEntity<List<StockResponseDTO>> listarStock() {

        return ResponseEntity.ok(stockService.listarStock());
    }

    // Actualizar quantidade mínima
    @PatchMapping("/{produtoId}/quantidade-minima")
    public ResponseEntity<StockResponseDTO> actualizarQuantidadeMinima(
            @PathVariable Long produtoId,
            @RequestParam Integer quantidadeMinima) {

        return ResponseEntity.ok(
                stockService.actualizarQuantidadeMinima(
                        produtoId, quantidadeMinima));
    }

    // Registar entrada de mercadoria
    @PostMapping("/{produtoId}/entradas")
    public ResponseEntity<StockResponseDTO> registrarEntrada(
            @PathVariable Long produtoId,
            @RequestParam Integer quantidade,
            @RequestParam String motivo) {

        return ResponseEntity.ok(
                stockService.registrarEntrada(
                        produtoId, quantidade, motivo));
    }

    // Registar saída de mercadoria
    @PostMapping("/{produtoId}/saidas")
    public ResponseEntity<StockResponseDTO> registrarSaida(
            @PathVariable Long produtoId,
            @RequestParam Integer quantidade,
            @RequestParam String motivo) {

        return ResponseEntity.ok(
                stockService.registrarSaida(
                        produtoId, quantidade, motivo));
    }

    // Consultar histórico de movimentos
    @GetMapping("/{produtoId}/movimentos")
    public ResponseEntity<List<MovimentoEstoqueResponseDTO>> listarMovimentos(
            @PathVariable Long produtoId) {

        return ResponseEntity.ok(
                stockService.listarMovimentos(produtoId));
    }
}
