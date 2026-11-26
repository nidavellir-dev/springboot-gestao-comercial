
package com.davidandredaniel.springbootaula01.controller;

import com.davidandredaniel.springbootaula01.dto.CategoriaResponseDTO;
import com.davidandredaniel.springbootaula01.service.CategoriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@Tag(
        name = "Categorias",
        description = "Operações de gestão, consulta, criação, actualização e eliminação de categorias."
)
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService categoriaService) {
        this.service = categoriaService;
    }

    // 1. Listar todas as categorias
    @Operation(
            summary = "Listar todas as categorias",
            description = "Devolve todas as categorias registadas no sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de categorias devolvida com sucesso."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    // 2. Consultar categoria pelo ID
    @Operation(
            summary = "Consultar categoria por ID",
            description = "Pesquisa uma categoria através do seu identificador único."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria encontrada."),
            @ApiResponse(responseCode = "400", description = "Identificador inválido."),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> buscarPorId(

            @Parameter(
                    description = "Identificador único da categoria",
                    example = "1"
            )
            @PathVariable Long id) {

        CategoriaResponseDTO categoriaResponseDTO = service.buscaPorId(id);

        return ResponseEntity.ok(categoriaResponseDTO);
    }

    // 3. Criar categoria
    @Operation(
            summary = "Criar categoria",
            description = "Regista uma nova categoria no sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados da categoria inválidos.")
    })
    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> criar(
            @Valid @RequestBody CategoriaResponseDTO dto) {

        CategoriaResponseDTO novaCategoria = service.criar(dto);

        return ResponseEntity.status(201).body(novaCategoria);
    }

    // 4. Actualizar categoria
    @Operation(
            summary = "Actualizar categoria",
            description = "Actualiza os dados de uma categoria existente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria actualizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados de actualização inválidos."),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> atualizar(

            @Parameter(
                    description = "Identificador da categoria a actualizar",
                    example = "1"
            )
            @PathVariable Long id,

            @Valid @RequestBody CategoriaResponseDTO dto) {

        CategoriaResponseDTO categoriaActualizada =
                service.atualizarCategoria(id, dto);

        return ResponseEntity.ok(categoriaActualizada);
    }

    // 5. Eliminar categoria
    @Operation(
            summary = "Eliminar categoria",
            description = "Remove uma categoria através do seu identificador."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Categoria eliminada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(

            @Parameter(
                    description = "Identificador da categoria a eliminar",
                    example = "1"
            )
            @PathVariable Long id) {

        service.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}
