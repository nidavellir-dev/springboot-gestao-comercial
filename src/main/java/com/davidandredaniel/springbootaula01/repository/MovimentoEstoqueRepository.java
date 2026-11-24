
package com.davidandredaniel.springbootaula01.repository;

import com.davidandredaniel.springbootaula01.entity.MovimentoEstoque;
import com.davidandredaniel.springbootaula01.entity.TipoMovimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimentoEstoqueRepository
        extends JpaRepository<MovimentoEstoque, Long> {

    List<MovimentoEstoque> findByStockIdOrderByDataMovimentoDesc(Long stockId);

    List<MovimentoEstoque> findByTipoOrderByDataMovimentoDesc(TipoMovimento tipo);
}

