package com.davidandredaniel.springbootaula01.exception;

public class ProdutoNaoEncontradoException extends RuntimeException{

    public ProdutoNaoEncontradoException(String mensagem){
       super(mensagem);
    }
}
