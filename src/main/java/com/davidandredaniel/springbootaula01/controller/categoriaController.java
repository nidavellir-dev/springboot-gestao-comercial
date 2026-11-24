package com.davidandredaniel.springbootaula01.controller;

import com.davidandredaniel.springbootaula01.dto.CategoriaResponseDTO;
import com.davidandredaniel.springbootaula01.entity.Categoria;
import com.davidandredaniel.springbootaula01.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class categoriaController {
    private final CategoriaService service;

    public categoriaController(CategoriaService CategoriaService) {
        this.service = CategoriaService;
    }

    //    1. listar Categoria
    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    //    2. buscar categoria pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> buscarPorId(@PathVariable Long id) {
        CategoriaResponseDTO categoriaResponseDTO = service.buscaPorId(id);
        return ResponseEntity.ok(categoriaResponseDTO);
    }

    //    3. criar categoria
    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> criar(@Valid @RequestBody CategoriaResponseDTO dto) {
        CategoriaResponseDTO novaCategoria = service.criar(dto);
        return ResponseEntity.status(201).body(novaCategoria);
    }

    //    4. Actualizar categoria
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody CategoriaResponseDTO dto) {
        CategoriaResponseDTO categoriaActualizada = service.atualizarCategoria(id, dto);
        return ResponseEntity.ok(categoriaActualizada);
    }

    //    6. Eliminar categoria
    @DeleteMapping("/{id}")
    public ResponseEntity<Valid> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
