
package com.davidandredaniel.springbootaula01.controller;

import com.davidandredaniel.springbootaula01.dto.ProdutoRequestDTO;
import com.davidandredaniel.springbootaula01.dto.ProdutoResponseDTO;
import com.davidandredaniel.springbootaula01.service.ProdutoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@Tag(
        name = "Produtos",
        description = "Operações de gestão, consulta, pesquisa e actualização de produtos."
)
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService produtoService) {
        this.service = produtoService;
    }

    // 1. Listar todos os produtos
    @Operation(
            summary = "Listar todos os produtos",
            description = "Devolve a lista completa de produtos registados no sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de produtos devolvida com sucesso."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    // 2. Listar produtos por categoria
    @Operation(
            summary = "Listar produtos por categoria",
            description = "Devolve todos os produtos associados a uma determinada categoria."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produtos encontrados."),
            @ApiResponse(responseCode = "400", description = "Identificador de categoria inválido.")
    })
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<ProdutoResponseDTO>> listarPorCategoris(
            @Parameter(description = "Identificador da categoria", example = "1")
            @PathVariable Long categoriaId) {

        return ResponseEntity.ok(service.listarCategoris(categoriaId));
    }

    // 3. Pesquisar produtos por preço superior
    @Operation(
            summary = "Pesquisar produtos por preço",
            description = "Devolve produtos cujo preço seja superior ao valor informado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pesquisa realizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Preço inválido ou parâmetro em falta.")
    })
    @GetMapping("/preco-superior")
    public ResponseEntity<List<ProdutoResponseDTO>> listarPorPrecoMaisAltio(
            @Parameter(description = "Preço de referência para a pesquisa", example = "50000")
            @RequestParam Double preco) {

        return ResponseEntity.ok(service.ListarPorPrecoSuperior(preco));
    }

    // 4. Pesquisar produtos por nome
    @Operation(
            summary = "Pesquisar produtos por nome",
            description = "Pesquisa produtos através de uma parte ou da totalidade do nome."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pesquisa realizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Nome não informado ou inválido.")
    })
    @GetMapping("/pequisar")
    public ResponseEntity<List<ProdutoResponseDTO>> listarPorNome(
            @Parameter(description = "Nome ou parte do nome do produto", example = "Samsung")
            @RequestParam String nome) {

        return ResponseEntity.ok(service.ListarPorNome(nome));
    }

    // 5. Listar produtos com paginação e ordenação
    @Operation(
            summary = "Listar produtos paginados",
            description = "Devolve os produtos organizados em páginas, permitindo definir o tamanho e a ordenação."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Página de produtos devolvida com sucesso."),
            @ApiResponse(responseCode = "400", description = "Parâmetros de paginação ou ordenação inválidos.")
    })
    @GetMapping("/paginados")
    public ResponseEntity<Page<ProdutoResponseDTO>> listarPaginado(

            @Parameter(description = "Número da página, começando em zero", example = "0")
            @RequestParam(defaultValue = "0") int pagina,

            @Parameter(description = "Quantidade de produtos por página", example = "10")
            @RequestParam(defaultValue = "10") int tamanho,

            @Parameter(description = "Campo utilizado para ordenação: id, nome ou preco", example = "nome")
            @RequestParam(defaultValue = "id") String campoOrdenacao,

            @Parameter(description = "Direcção da ordenação: asc ou desc", example = "asc")
            @RequestParam(defaultValue = "asc") String direcao) {

        Page<ProdutoResponseDTO> resultado = service.listarPaginado(
                pagina,
                tamanho,
                campoOrdenacao,
                direcao
        );

        return ResponseEntity.ok(resultado);
    }

    // 6. Pesquisar produtos por preço utilizando JPQL
    @Operation(
            summary = "Pesquisar produtos por preço com JPQL",
            description = "Utiliza uma consulta JPQL para devolver produtos cujo preço seja superior ao valor informado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pesquisa JPQL realizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Preço negativo ou parâmetro inválido.")
    })
    @GetMapping("/jpql/preco-superior")
    public List<ProdutoResponseDTO> listarPorPrecoSuperior(

            @Parameter(description = "Preço mínimo de referência", example = "50000")
            @RequestParam
            @PositiveOrZero(message = "O preço não pode ser negativo.")
            Double preco) {

        return service.buscarPorPrecoSuperior(preco);
    }

    // 7. Pesquisar produtos por categoria utilizando JPQL
    @Operation(
            summary = "Pesquisar produtos por categoria com JPQL",
            description = "Utiliza uma consulta JPQL para encontrar os produtos associados à categoria informada."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pesquisa JPQL realizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Identificador de categoria inválido.")
    })
    @GetMapping("/jpql/categoria/{categoriaId}")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarCategoriaJPQL(

            @Parameter(description = "Identificador da categoria", example = "1")
            @PathVariable Long categoriaId) {

        return ResponseEntity.ok(service.buscarPorCategoriaJPQL(categoriaId));
    }

    // 8. Buscar um produto pelo ID
    @Operation(
            summary = "Consultar produto por ID",
            description = "Pesquisa um produto através do seu identificador único."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto encontrado."),
            @ApiResponse(responseCode = "400", description = "Identificador inválido."),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(

            @Parameter(description = "Identificador único do produto", example = "1")
            @PathVariable Long id) {

        ProdutoResponseDTO produto = service.buscaPorId(id);

        return ResponseEntity.ok(produto);
    }

    // 9. Criar um produto
    @Operation(
            summary = "Registar novo produto",
            description = "Cria um novo produto e associa-o à categoria indicada no DTO."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados do produto inválidos."),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada.")
    })
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criar(
            @Valid @RequestBody ProdutoRequestDTO dto) {

        ProdutoResponseDTO novoProduto = service.criar(dto);

        return ResponseEntity.status(201).body(novoProduto);
    }

    // 10. Actualizar um produto
    @Operation(
            summary = "Actualizar produto",
            description = "Actualiza os dados de um produto existente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto actualizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados de actualização inválidos."),
            @ApiResponse(responseCode = "404", description = "Produto ou categoria não encontrados.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizar(

            @Parameter(description = "Identificador do produto a actualizar", example = "1")
            @PathVariable Long id,

            @Valid @RequestBody ProdutoRequestDTO dto) {

        ProdutoResponseDTO produtoActualizado =
                service.actualizarProduto(id, dto);

        return ResponseEntity.ok(produtoActualizado);
    }

    // 11. Eliminar um produto
    @Operation(
            summary = "Eliminar produto",
            description = "Remove um produto através do seu identificador."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Produto eliminado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(

            @Parameter(description = "Identificador do produto a eliminar", example = "1")
            @PathVariable Long id) {

        service.eleminar(id);

        return ResponseEntity.noContent().build();
    }
}
