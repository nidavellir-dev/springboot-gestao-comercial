
package com.davidandredaniel.springbootaula01.controller;

import com.davidandredaniel.springbootaula01.dto.VendaRequestDTO;
import com.davidandredaniel.springbootaula01.dto.VendaResponseDTO;
import com.davidandredaniel.springbootaula01.service.VendaService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendas")
public class VendaController {

    private final VendaService vendaService;

    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    // REGISTAR VENDA
    @PostMapping
    public ResponseEntity<VendaResponseDTO> criarVenda(
            @Valid @RequestBody VendaRequestDTO dto) {

        VendaResponseDTO venda =
                vendaService.criarVenda(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(venda);
    }

    // LISTAR VENDAS
    @GetMapping
    public ResponseEntity<List<VendaResponseDTO>> listarVendas() {

        return ResponseEntity.ok(
                vendaService.listarVendas()
        );
    }

    // BUSCAR VENDA POR ID
    @GetMapping("/{id}")
    public ResponseEntity<VendaResponseDTO> buscarVendaPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                vendaService.buscarVendaPorId(id)
        );
    }
}

