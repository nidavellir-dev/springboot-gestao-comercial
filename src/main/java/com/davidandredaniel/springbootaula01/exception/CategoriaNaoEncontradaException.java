package com.davidandredaniel.springbootaula01.exception;

public class CategoriaNaoEncontradaException extends RuntimeException {
    public CategoriaNaoEncontradaException(String message) {
        super(message);
    }
}
