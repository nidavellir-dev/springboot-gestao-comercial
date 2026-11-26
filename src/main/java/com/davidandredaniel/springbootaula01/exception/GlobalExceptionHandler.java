
package com.davidandredaniel.springbootaula01.exception;

import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 1. Tratar erros de validação dos DTOs
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

        resposta.put("status", HttpStatus.BAD_REQUEST.value());
        resposta.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        resposta.put("mensagem", "Existem erros de validação.");
        resposta.put("erros", erros);

        return ResponseEntity.badRequest().body(resposta);
    }

    // 2. Tratar produtos não encontrados
    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> tratarProdutoNaoEncontrado(
            ProdutoNaoEncontradoException exception) {

        return construirRespostaErro(
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );
    }

    // 3. Tratar categorias não encontradas
    @ExceptionHandler(CategoriaNaoEncontradaException.class)
    public ResponseEntity<Map<String, Object>> tratarCategoriaNaoEncontrada(
            CategoriaNaoEncontradaException exception) {

        return construirRespostaErro(
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );
    }

    // 4. Tratar vendas não encontradas
    @ExceptionHandler(VendaNaoEncontradaException.class)
    public ResponseEntity<Map<String, Object>> tratarVendaNaoEncontrada(
            VendaNaoEncontradaException exception) {

        return construirRespostaErro(
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );
    }

    // 5. Tratar argumentos inválidos
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> tratarArgumentoInvalido(
            IllegalArgumentException exception) {

        return construirRespostaErro(
                HttpStatus.BAD_REQUEST,
                exception.getMessage()
        );
    }

    // 6. Tratar erros inesperados
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> tratarErroInterno(
            Exception exception) {

        logger.error("Ocorreu um erro inesperado na aplicação.", exception);

        return construirRespostaErro(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocorreu um erro interno. Tente novamente mais tarde."
        );
    }

    // 7. Construir respostas de erro padronizadas
    private ResponseEntity<Map<String, Object>> construirRespostaErro(
            HttpStatus status,
            String mensagem) {

        Map<String, Object> resposta = new LinkedHashMap<>();

        resposta.put("status", status.value());
        resposta.put("error", status.getReasonPhrase());
        resposta.put("mensagem", mensagem);

        return ResponseEntity.status(status).body(resposta);
    }

    // Tratar incompatibilidade de tipos nos parâmetros
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> tratarTipoInvalido(
            MethodArgumentTypeMismatchException exception) {

        String mensagem = "O parâmetro '" + exception.getName()
                + "' recebeu um valor inválido: '"
                + exception.getValue() + "'.";

        return construirRespostaErro(
                HttpStatus.BAD_REQUEST,
                mensagem
        );
    }

    // Tratar parâmetros obrigatórios em falta
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, Object>> tratarParametroEmFalta(
            MissingServletRequestParameterException exception) {

        String mensagem = "O parâmetro obrigatório '"
                + exception.getParameterName()
                + "' não foi informado.";

        return construirRespostaErro(
                HttpStatus.BAD_REQUEST,
                mensagem
        );
    }

    // Tratar JSON malformado ou corpo da requisição inválido
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> tratarJsonInvalido(
            HttpMessageNotReadableException exception) {

        return construirRespostaErro(
                HttpStatus.BAD_REQUEST,
                "O corpo da requisição contém um JSON inválido ou malformado."
        );
    }

    // Tratar violações de validação dos parâmetros
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> tratarViolacaoDeValidacao(
            ConstraintViolationException exception) {

        return construirRespostaErro(
                HttpStatus.BAD_REQUEST,
                exception.getMessage()
        );
    }

   
    // Tratar erros de validação dos parâmetros dos métodos
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<Map<String, Object>> tratarValidacaoDeMetodo(
            HandlerMethodValidationException exception) {

        String mensagem = exception.getAllErrors()
                .stream()
                .map(erro -> erro.getDefaultMessage())
                .filter(texto -> texto != null && !texto.isBlank())
                .distinct()
                .collect(java.util.stream.Collectors.joining("; "));

        if (mensagem.isBlank()) {
            mensagem = "Um ou mais parâmetros da requisição são inválidos.";
        }

        return construirRespostaErro(
                HttpStatus.BAD_REQUEST,
                mensagem
        );
    }
}
