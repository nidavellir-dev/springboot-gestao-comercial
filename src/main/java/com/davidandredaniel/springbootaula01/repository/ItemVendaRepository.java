
package com.davidandredaniel.springbootaula01.repository;

import com.davidandredaniel.springbootaula01.entity.ItemVenda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemVendaRepository
        extends JpaRepository<ItemVenda, Long> {

    List<ItemVenda> findByVendaId(Long vendaId);
}
