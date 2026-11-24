package com.davidandredaniel.springbootaula01.repository;

import com.davidandredaniel.springbootaula01.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByProdutoId(Long produtoId);

    boolean existsByProdutoId(Long produtoId);
}
