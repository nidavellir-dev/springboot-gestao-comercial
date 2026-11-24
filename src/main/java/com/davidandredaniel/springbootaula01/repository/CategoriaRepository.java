package com.davidandredaniel.springbootaula01.repository;

import com.davidandredaniel.springbootaula01.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
