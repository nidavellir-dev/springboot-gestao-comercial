
package com.davidandredaniel.springbootaula01.controller;

import com.davidandredaniel.springbootaula01.dto.StockRequestDTO;
import com.davidandredaniel.springbootaula01.dto.StockResponseDTO;
import com.davidandredaniel.springbootaula01.dto.MovimentoEstoqueResponseDTO;
import com.davidandredaniel.springbootaula01.service.StockService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estoque")
@Tag(
        name = "Stock",
        description = "Operações de gestão, consulta, entradas, saídas e acompanhamento do stock."
)
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    // 1. Criar stock para um produto
    @Operation(
            summary = "Criar stock de um produto",
            description = "Regista um novo controlo de stock para um produto."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Stock criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados de stock inválidos."),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado.")
    })
    @PostMapping
    public ResponseEntity<StockResponseDTO> criarStock(
            @Valid @RequestBody StockRequestDTO request) {

        StockResponseDTO resposta = stockService.criarStock(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    // 2. Consultar stock de um produto
    @Operation(
            summary = "Consultar stock de um produto",
            description = "Devolve as informações de stock associadas ao produto indicado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Stock consultado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Stock ou produto não encontrado.")
    })
    @GetMapping("/{produtoId}")
    public ResponseEntity<StockResponseDTO> consultarStock(

            @Parameter(
                    description = "Identificador do produto",
                    example = "3"
            )
            @PathVariable Long produtoId) {

        return ResponseEntity.ok(
                stockService.consultarStock(produtoId));
    }

    // 3. Listar todos os stocks
    @Operation(
            summary = "Listar todos os stocks",
            description = "Devolve os registos de stock existentes no sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de stocks devolvida com sucesso.")
    })
    @GetMapping
    public ResponseEntity<List<StockResponseDTO>> listarStock() {

        return ResponseEntity.ok(stockService.listarStock());
    }

    // 4. Actualizar quantidade mínima
    @Operation(
            summary = "Actualizar quantidade mínima",
            description = "Actualiza o limite mínimo de stock definido para um produto."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Quantidade mínima actualizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Quantidade mínima inválida."),
            @ApiResponse(responseCode = "404", description = "Stock ou produto não encontrado.")
    })
    @PatchMapping("/{produtoId}/quantidade-minima")
    public ResponseEntity<StockResponseDTO> actualizarQuantidadeMinima(

            @Parameter(
                    description = "Identificador do produto",
                    example = "3"
            )
            @PathVariable Long produtoId,

            @Parameter(
                    description = "Nova quantidade mínima de stock",
                    example = "10"
            )
            @RequestParam Integer quantidadeMinima) {

        return ResponseEntity.ok(
                stockService.actualizarQuantidadeMinima(
                        produtoId, quantidadeMinima));
    }

    // 5. Registar entrada de mercadoria
    @Operation(
            summary = "Registar entrada de mercadoria",
            description = "Regista uma entrada de mercadoria e actualiza a quantidade disponível em stock."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Entrada registada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Quantidade ou motivo inválido."),
            @ApiResponse(responseCode = "404", description = "Produto ou stock não encontrado.")
    })
    @PostMapping("/{produtoId}/entradas")
    public ResponseEntity<StockResponseDTO> registrarEntrada(

            @Parameter(
                    description = "Identificador do produto",
                    example = "3"
            )
            @PathVariable Long produtoId,

            @Parameter(
                    description = "Quantidade de mercadoria recebida",
                    example = "20"
            )
            @RequestParam Integer quantidade,

            @Parameter(
                    description = "Motivo da entrada de mercadoria",
                    example = "Reposição de stock"
            )
            @RequestParam String motivo) {

        return ResponseEntity.ok(
                stockService.registrarEntrada(
                        produtoId, quantidade, motivo));
    }

    // 6. Registar saída de mercadoria
    @Operation(
            summary = "Registar saída de mercadoria",
            description = "Regista uma saída de mercadoria e actualiza a quantidade disponível em stock."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Saída registada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Quantidade inválida ou stock insuficiente."),
            @ApiResponse(responseCode = "404", description = "Produto ou stock não encontrado.")
    })
    @PostMapping("/{produtoId}/saidas")
    public ResponseEntity<StockResponseDTO> registrarSaida(

            @Parameter(
                    description = "Identificador do produto",
                    example = "3"
            )
            @PathVariable Long produtoId,

            @Parameter(
                    description = "Quantidade de mercadoria retirada",
                    example = "5"
            )
            @RequestParam Integer quantidade,

            @Parameter(
                    description = "Motivo da saída de mercadoria",
                    example = "Venda de produtos"
            )
            @RequestParam String motivo) {

        return ResponseEntity.ok(
                stockService.registrarSaida(
                        produtoId, quantidade, motivo));
    }

    // 7. Consultar histórico de movimentos
    @Operation(
            summary = "Consultar histórico de movimentos",
            description = "Devolve o histórico de entradas e saídas de stock de um produto."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Histórico de movimentos devolvido com sucesso."),
            @ApiResponse(responseCode = "404", description = "Produto ou stock não encontrado.")
    })
    @GetMapping("/{produtoId}/movimentos")
    public ResponseEntity<List<MovimentoEstoqueResponseDTO>> listarMovimentos(

            @Parameter(
                    description = "Identificador do produto",
                    example = "3"
            )
            @PathVariable Long produtoId) {

        return ResponseEntity.ok(
                stockService.listarMovimentos(produtoId));
    }
}
