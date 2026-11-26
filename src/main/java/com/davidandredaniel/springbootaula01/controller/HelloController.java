
package com.davidandredaniel.springbootaula01.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(
        name = "Informações Gerais",
        description = "Endpoints de apresentação e informações sobre a formação."
)
public class HelloController {

    // 1. Mensagem de boas-vindas
    @Operation(
            summary = "Mensagem de boas-vindas",
            description = "Apresenta uma mensagem de boas-vindas à formação Spring Boot."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Mensagem devolvida com sucesso."
            )
    })
    @GetMapping("/hello")
    public String hello() {
        return "olá! David, Bem-vindo a tua forma;'ao Spring Boot";
    }

    // 2. Informações sobre o curso
    @Operation(
            summary = "Consultar informações do curso",
            description = "Apresenta informações sobre a formação profissional em Spring Boot."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Informações do curso devolvidas com sucesso."
            )
    })
    @GetMapping("/curso")
    public String curso() {
        return "formação profissional em Spring Boot";
    }

    // 3. Informações sobre o aluno
    @Operation(
            summary = "Consultar informações do aluno",
            description = "Devolve informações de apresentação do aluno e das tecnologias utilizadas."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Informações do aluno devolvidas com sucesso."
            )
    })
    @GetMapping("/aluno")
    public String aluno() {
        return "\n nome: David\n " +
                "Tecnologia: Java\n " +
                "Formação: Spring Boot";
    }
}
