package com.davidandredaniel.springbootaula01.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "olá! David, Bem-vindo a tua forma;'ao Spring Boot";
    }

    @GetMapping("/curso")
    public String curso(){
        return "formação profissional em Spring Boot";
    }

    @GetMapping ("/aluno")
    public String aluno(){
        return "\n nome: David\n " +
                "Tecnologia: Java\n " +
                "Formação: Spring Boot";
    }
}
