package com.davidandredaniel.springbootaula01.service;


import com.davidandredaniel.springbootaula01.dto.CategoriaResponseDTO;
import com.davidandredaniel.springbootaula01.entity.Categoria;
import com.davidandredaniel.springbootaula01.exception.CategoriaNaoEncontradaException;
import com.davidandredaniel.springbootaula01.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {
    private CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    //    1. criar categoria
    public CategoriaResponseDTO criar(CategoriaResponseDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNome(categoria.getNome());
        Categoria produtoGuardado = categoriaRepository.save(categoria);
        return converterParaDTO(produtoGuardado);
    }


    //    3. listar categoria
    public List<CategoriaResponseDTO> listarTodos() {
        return categoriaRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    //    3. buscar uma categoria por ID
    public CategoriaResponseDTO buscaPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNaoEncontradaException(
                        "A categoria com o ID " + id + " não foi encontrado."));
        return converterParaDTO(categoria);
    }
    //    3. Actualizar categoria

    public CategoriaResponseDTO atualizarCategoria(Long id, CategoriaResponseDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNaoEncontradaException(
                        "A categoria com o ID " + id + " não foi encontrado."
                ));


        categoria.setNome(dto.getNome());
        Categoria salvarCategoria = categoriaRepository.save(categoria);
        return converterParaDTO(salvarCategoria);
    }

    //    3. Eliminar uma categoria
    public void eliminar(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new CategoriaNaoEncontradaException(
                    "A categoria com o ID " + id + " não foi encontrado."
            );
        }
        categoriaRepository.deleteById(id);
    }

    //    3. Converter uma Idesntidade Categoria em DTO
    public CategoriaResponseDTO converterParaDTO(Categoria categoria) {
        return new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getNome()
        );
    }
}
