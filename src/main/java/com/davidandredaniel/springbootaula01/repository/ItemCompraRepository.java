package com.davidandredaniel.springbootaula01.repository;

import com.davidandredaniel.springbootaula01.entity.ItemCompra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemCompraRepository extends JpaRepository<ItemCompra, Long> {

    List<ItemCompra> findByCompraId(Long compraId);
}