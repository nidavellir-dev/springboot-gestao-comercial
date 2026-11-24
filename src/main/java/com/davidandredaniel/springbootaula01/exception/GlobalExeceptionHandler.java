
package com.davidandredaniel.springbootaula01.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExeceptionHandler {

    // Tratar erros de validação dos DTOs
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> tratarErrosValidacao(
            MethodArgumentNotValidException exception) {

        Map<String, String> erros = new LinkedHashMap<>();

        exception.getBindingResult().getFieldErrors()
                .forEach(erro -> erros.put(
                        erro.getField(),
                        erro.getDefaultMessage()
                ));

        Map<String, Object> resposta = new LinkedHashMap<>();

        resposta.put("status", 400);
        resposta.put("mensagem", "Existem erros de validação");
        resposta.put("erros", erros);

        return ResponseEntity.badRequest().body(resposta);
    }

    // Tratar produtos não encontrados
    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> tratarErroNaoEncontrado(
            ProdutoNaoEncontradoException exception) {

        Map<String, Object> resposta = new LinkedHashMap<>();

        resposta.put("status", 404);
        resposta.put("mensagem", exception.getMessage());

        return ResponseEntity.status(404).body(resposta);
    }

    //  Tratar argumentos inválidos
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> tratarArgumentoInvalido(
            IllegalArgumentException exception) {

        Map<String, Object> resposta = new LinkedHashMap<>();

        resposta.put("status", 400);
        resposta.put("error", "Bad Request");
        resposta.put("mensagem", exception.getMessage());

        return ResponseEntity.badRequest().body(resposta);
    }

    // Tratar categoria não encontrados
    @ExceptionHandler(CategoriaNaoEncontradaException.class)
    public ResponseEntity<Map<String, Object>> tratarErroNaoEncontrado(
            CategoriaNaoEncontradaException exception) {

        Map<String, Object> resposta = new LinkedHashMap<>();

        resposta.put("status", 404);
        resposta.put("mensagem", exception.getMessage());

        return ResponseEntity.status(404).body(resposta);
    }

    // Tratar vendas não encontrados
    @ExceptionHandler(VendaNaoEncontradaException.class)
    public ResponseEntity<Map<String, Object>> VendaNaoEncontrada(
            VendaNaoEncontradaException exception) {

        Map<String, Object> resposta = new LinkedHashMap<>();

        resposta.put("status", 404);
        resposta.put("mensagem", exception.getMessage());

        return ResponseEntity.status(404).body(resposta);
    }
}
