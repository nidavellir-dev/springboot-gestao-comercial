package com.davidandredaniel.springbootaula01.controller;

import com.davidandredaniel.springbootaula01.dto.CompraRequestDTO;
import com.davidandredaniel.springbootaula01.dto.CompraResponseDTO;
import com.davidandredaniel.springbootaula01.service.CompraService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/compras")
public class CompraController {

    private final CompraService compraService;

    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    @PostMapping
    public ResponseEntity<CompraResponseDTO> registarCompra(
            @Valid @RequestBody CompraRequestDTO dto
    ) {
        CompraResponseDTO resposta = compraService.registarCompra(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resposta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompraResponseDTO> consultarCompra(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                compraService.consultarCompra(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<CompraResponseDTO>> listarCompras() {

        return ResponseEntity.ok(
                compraService.listarCompras()
        );
    }
}