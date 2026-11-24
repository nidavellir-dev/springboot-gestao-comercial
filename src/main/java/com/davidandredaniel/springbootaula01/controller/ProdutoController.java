package com.davidandredaniel.springbootaula01.controller;

import com.davidandredaniel.springbootaula01.dto.ProdutoRequestDTO;
import com.davidandredaniel.springbootaula01.dto.ProdutoResponseDTO;
import com.davidandredaniel.springbootaula01.service.ProdutoService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService produtoService) {
        this.service = produtoService;
    }

    // 1. Listar todos os produtos
    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    // 2. Listar produto por categoria
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<ProdutoResponseDTO>> listarPorCategoris(@PathVariable Long categoriaId) {
        return ResponseEntity.ok(service.listarCategoris(categoriaId));
    }

    // 9. Pesquisa produto por preço mais alto
    @GetMapping("/preco-superior")
    public ResponseEntity<List<ProdutoResponseDTO>> listarPorPrecoMaisAltio(@RequestParam Double preco) {
        return ResponseEntity.ok(service.ListarPorPrecoSuperior(preco));
    }

    @GetMapping("/pequisar")
    public ResponseEntity<List<ProdutoResponseDTO>> listarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(service.ListarPorNome(nome));
    }


    // 3. Listar produtos com paginação e ordenação
    @GetMapping("/paginados")
    public ResponseEntity<Page<ProdutoResponseDTO>> listarPaginado(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanho,
            @RequestParam(defaultValue = "id") String campoOrdenacao,
            @RequestParam(defaultValue = "asc") String direcao) {

        Page<ProdutoResponseDTO> resultado = service.listarPaginado(
                pagina,
                tamanho,
                campoOrdenacao,
                direcao
        );

        return ResponseEntity.ok(resultado);
    }

    // métodoos especiais de listagem de produtos usando Query
    @GetMapping("/jpql/preco-superior")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarPorPrecoSuperior(@RequestParam Double preco) {
        return ResponseEntity.ok(service.ListarPorPrecoSuperior(preco));
    }

    @GetMapping("/jpql/categoria/{categoriaId}")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarCategoriaJPQL(@PathVariable Long categoriaId) {
        return ResponseEntity.ok(service.buscarPorCategoriaJPQL(categoriaId));
    }

    // 3. Buscar um produto pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(
            @PathVariable Long id) {

        ProdutoResponseDTO produto = service.buscaPorId(id);

        return ResponseEntity.ok(produto);
    }

    // 4. Criar um produto
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criar(
            @Valid @RequestBody ProdutoRequestDTO dto) {

        ProdutoResponseDTO novoProduto = service.criar(dto);

        return ResponseEntity.status(201).body(novoProduto);
    }

    // 5. Actualizar um produto
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProdutoRequestDTO dto) {

        ProdutoResponseDTO produtoActualizado =
                service.actualizarProduto(id, dto);

        return ResponseEntity.ok(produtoActualizado);
    }

    // 6. Eliminar um produto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        service.eleminar(id);

        return ResponseEntity.noContent().build();
    }
}