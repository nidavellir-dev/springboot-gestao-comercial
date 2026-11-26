
package com.davidandredaniel.springbootaula01.controller;

import com.davidandredaniel.springbootaula01.dto.CompraRequestDTO;
import com.davidandredaniel.springbootaula01.dto.CompraResponseDTO;
import com.davidandredaniel.springbootaula01.service.CompraService;

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
@RequestMapping("/compras")
@Tag(
        name = "Compras",
        description = "Operações de registo, consulta e gestão de compras."
)
public class CompraController {

    private final CompraService compraService;

    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    // 1. Registar compra
    @Operation(
            summary = "Registar nova compra",
            description = "Regista uma compra com os respectivos produtos e quantidades."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Compra registada com sucesso."
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados da compra inválidos."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Produto não encontrado."
            )
    })
    @PostMapping
    public ResponseEntity<CompraResponseDTO> registarCompra(
            @Valid @RequestBody CompraRequestDTO dto) {

        CompraResponseDTO resposta =
                compraService.registarCompra(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resposta);
    }

    // 2. Consultar compra por ID
    @Operation(
            summary = "Consultar compra por ID",
            description = "Pesquisa os detalhes de uma compra através do seu identificador único."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Compra encontrada."
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Identificador inválido."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Compra não encontrada."
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<CompraResponseDTO> consultarCompra(

            @Parameter(
                    description = "Identificador único da compra",
                    example = "1"
            )
            @PathVariable Long id) {

        return ResponseEntity.ok(
                compraService.consultarCompra(id)
        );
    }

    // 3. Listar compras
    @Operation(
            summary = "Listar todas as compras",
            description = "Devolve a lista de compras registadas no sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de compras devolvida com sucesso."
            )
    })
    @GetMapping
    public ResponseEntity<List<CompraResponseDTO>> listarCompras() {

        return ResponseEntity.ok(
                compraService.listarCompras()
        );
    }
}
