
package com.davidandredaniel.springbootaula01.controller;

import com.davidandredaniel.springbootaula01.dto.VendaRequestDTO;
import com.davidandredaniel.springbootaula01.dto.VendaResponseDTO;
import com.davidandredaniel.springbootaula01.service.VendaService;

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
@RequestMapping("/vendas")
@Tag(
        name = "Vendas",
        description = "Operações de registo, consulta e gestão de vendas."
)
public class VendaController {

    private final VendaService vendaService;

    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    // 1. Registar venda
    @Operation(
            summary = "Registar nova venda",
            description = "Regista uma nova venda com os respectivos produtos e quantidades."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Venda registada com sucesso."
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados da venda inválidos."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Produto não encontrado."
            )
    })
    @PostMapping
    public ResponseEntity<VendaResponseDTO> criarVenda(
            @Valid @RequestBody VendaRequestDTO dto) {

        VendaResponseDTO venda =
                vendaService.criarVenda(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(venda);
    }

    // 2. Listar vendas
    @Operation(
            summary = "Listar todas as vendas",
            description = "Devolve a lista de vendas registadas no sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de vendas devolvida com sucesso."
            )
    })
    @GetMapping
    public ResponseEntity<List<VendaResponseDTO>> listarVendas() {

        return ResponseEntity.ok(
                vendaService.listarVendas()
        );
    }

    // 3. Consultar venda por ID
    @Operation(
            summary = "Consultar venda por ID",
            description = "Pesquisa os detalhes de uma venda através do seu identificador único."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Venda encontrada."
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Identificador inválido."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Venda não encontrada."
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<VendaResponseDTO> buscarVendaPorId(

            @Parameter(
                    description = "Identificador único da venda",
                    example = "1"
            )
            @PathVariable Long id) {

        return ResponseEntity.ok(
                vendaService.buscarVendaPorId(id)
        );
    }
}
