package com.davidandredaniel.springbootaula01.exception;

public class VendaNaoEncontradaException extends RuntimeException {
    public VendaNaoEncontradaException(String message) {
        super(message);
    }
}
