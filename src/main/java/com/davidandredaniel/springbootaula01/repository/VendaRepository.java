
package com.davidandredaniel.springbootaula01.repository;

import com.davidandredaniel.springbootaula01.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long> {

    List<Venda> findAllByOrderByDataVendaDesc();
}
