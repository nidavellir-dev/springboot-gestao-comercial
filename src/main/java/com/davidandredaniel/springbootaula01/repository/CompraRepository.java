package com.davidandredaniel.springbootaula01.repository;

import com.davidandredaniel.springbootaula01.entity.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraRepository extends JpaRepository<Compra, Long> {
}