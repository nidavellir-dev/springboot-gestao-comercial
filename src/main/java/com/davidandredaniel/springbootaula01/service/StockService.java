
package com.davidandredaniel.springbootaula01.service;

import com.davidandredaniel.springbootaula01.dto.StockRequestDTO;
import com.davidandredaniel.springbootaula01.dto.StockResponseDTO;
import com.davidandredaniel.springbootaula01.dto.MovimentoEstoqueResponseDTO;

import com.davidandredaniel.springbootaula01.entity.MovimentoEstoque;
import com.davidandredaniel.springbootaula01.entity.Produto;
import com.davidandredaniel.springbootaula01.entity.Stock;
import com.davidandredaniel.springbootaula01.entity.TipoMovimento;

import com.davidandredaniel.springbootaula01.repository.MovimentoEstoqueRepository;
import com.davidandredaniel.springbootaula01.repository.ProdutoRepository;
import com.davidandredaniel.springbootaula01.repository.StockRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final ProdutoRepository produtoRepository;
    private final MovimentoEstoqueRepository movimentoEstoqueRepository;

    public StockService(
            StockRepository stockRepository,
            ProdutoRepository produtoRepository,
            MovimentoEstoqueRepository movimentoEstoqueRepository) {

        this.stockRepository = stockRepository;
        this.produtoRepository = produtoRepository;
        this.movimentoEstoqueRepository = movimentoEstoqueRepository;
    }

    // Criar stock para um produto
    @Transactional
    public StockResponseDTO criarStock(StockRequestDTO request) {

        Produto produto = produtoRepository.findById(request.getProdutoId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Produto não encontrado"));

        if (stockRepository.existsByProdutoId(request.getProdutoId())) {
            throw new IllegalArgumentException(
                    "Este produto já possui um registo de stock");
        }

        if (request.getQuantidadeMinima() == null
                || request.getQuantidadeMinima() < 0) {

            throw new IllegalArgumentException(
                    "A quantidade mínima não pode ser negativa ou nula");
        }

        Stock stock = new Stock();

        stock.setProduto(produto);
        stock.setQuantidade_actual(0);
        stock.setQuantidade_minima(request.getQuantidadeMinima());

        Stock stockGuardado = stockRepository.save(stock);

        return converterStock(stockGuardado);
    }

    // Consultar stock de um produto
    @Transactional(readOnly = true)
    public StockResponseDTO consultarStock(Long produtoId) {

        Stock stock = buscarStock(produtoId);

        return converterStock(stock);
    }

    // Listar todos os stocks
    @Transactional(readOnly = true)
    public List<StockResponseDTO> listarStock() {

        return stockRepository.findAll()
                .stream()
                .map(this::converterStock)
                .toList();
    }

    // Actualizar quantidade mínima
    @Transactional
    public StockResponseDTO actualizarQuantidadeMinima(
            Long produtoId, Integer quantidadeMinima) {

        if (quantidadeMinima == null || quantidadeMinima < 0) {
            throw new IllegalArgumentException(
                    "A quantidade mínima não pode ser negativa");
        }

        Stock stock = buscarStock(produtoId);

        stock.setQuantidade_minima(quantidadeMinima);

        Stock stockActualizado = stockRepository.save(stock);

        return converterStock(stockActualizado);
    }

    // Registar entrada de mercadoria
    @Transactional
    public StockResponseDTO registrarEntrada(
            Long produtoId, Integer quantidade, String motivo) {

        validarMovimento(quantidade, motivo);

        Stock stock = buscarStock(produtoId);

        stock.setQuantidade_actual(
                stock.getQuantidade_actual() + quantidade);

        registrarMovimento(
                stock, TipoMovimento.ENTRADA, quantidade, motivo);

        Stock stockActualizado = stockRepository.save(stock);

        return converterStock(stockActualizado);
    }

    // Registar saída de mercadoria
    @Transactional
    public StockResponseDTO registrarSaida(
            Long produtoId, Integer quantidade, String motivo) {

        validarMovimento(quantidade, motivo);

        Stock stock = buscarStock(produtoId);

        if (stock.getQuantidade_actual() < quantidade) {
            throw new IllegalArgumentException(
                    "Stock insuficiente para realizar esta operação");
        }

        stock.setQuantidade_actual(
                stock.getQuantidade_actual() - quantidade);

        registrarMovimento(
                stock, TipoMovimento.SAIDA, quantidade, motivo);

        Stock stockActualizado = stockRepository.save(stock);

        return converterStock(stockActualizado);
    }

    // Consultar histórico de movimentos de um produto
    @Transactional(readOnly = true)
    public List<MovimentoEstoqueResponseDTO> listarMovimentos(Long produtoId) {

        Stock stock = buscarStock(produtoId);

        return movimentoEstoqueRepository
                .findByStockIdOrderByDataMovimentoDesc(stock.getId())
                .stream()
                .map(this::converterMovimento)
                .toList();
    }

    // Procurar stock pelo ID do produto
    private Stock buscarStock(Long produtoId) {

        return stockRepository.findByProdutoId(produtoId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Stock não encontrado para este produto"));
    }

    // Converter entidade Stock em StockResponseDTO
    private StockResponseDTO converterStock(Stock stock) {

        return new StockResponseDTO(
                stock.getId(),
                stock.getProduto().getId(),
                stock.getProduto().getNome(),
                stock.getQuantidade_actual(),
                stock.getQuantidade_minima()
        );
    }

    // Converter entidade MovimentoEstoque em DTO
    private MovimentoEstoqueResponseDTO converterMovimento(
            MovimentoEstoque movimento) {

        return new MovimentoEstoqueResponseDTO(
                movimento.getId(),
                movimento.getStock().getProduto().getId(),
                movimento.getStock().getProduto().getNome(),
                movimento.getTipo(),
                movimento.getQuantidade(),
                movimento.getMotivo(),
                movimento.getDataMovimento()
        );
    }

    // Registar movimento no histórico
    private void registrarMovimento(
            Stock stock,
            TipoMovimento tipo,
            Integer quantidade,
            String motivo) {

        MovimentoEstoque movimento = new MovimentoEstoque();

        movimento.setStock(stock);
        movimento.setTipo(tipo);
        movimento.setQuantidade(quantidade);
        movimento.setMotivo(motivo);
        movimento.setDataMovimento(LocalDateTime.now());

        movimentoEstoqueRepository.save(movimento);
    }

    // Validar dados do movimento
    private void validarMovimento(Integer quantidade, String motivo) {

        if (quantidade == null || quantidade <= 0) {
            throw new IllegalArgumentException(
                    "A quantidade deve ser superior a zero");
        }

        if (motivo == null || motivo.isBlank()) {
            throw new IllegalArgumentException(
                    "O motivo do movimento é obrigatório");
        }
    }
}
