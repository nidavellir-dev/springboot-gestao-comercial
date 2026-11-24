
package com.davidandredaniel.springbootaula01.service;

import com.davidandredaniel.springbootaula01.dto.CategoriaResponseDTO;
import com.davidandredaniel.springbootaula01.dto.ProdutoRequestDTO;
import com.davidandredaniel.springbootaula01.dto.ProdutoResponseDTO;
import com.davidandredaniel.springbootaula01.entity.Categoria;
import com.davidandredaniel.springbootaula01.entity.Produto;
import com.davidandredaniel.springbootaula01.exception.ProdutoNaoEncontradoException;
import com.davidandredaniel.springbootaula01.repository.CategoriaRepository;
import com.davidandredaniel.springbootaula01.repository.ProdutoRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;
    private final CategoriaRepository categoriaRepository;

    public ProdutoService(
            ProdutoRepository repository,
            CategoriaRepository categoriaRepository) {

        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
    }

    // 1. Criar um produto
    public ProdutoResponseDTO criar(ProdutoRequestDTO dto) {

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "A categoria com o ID " + dto.getCategoriaId()
                                + " não foi encontrada."
                ));

        Produto produto = new Produto();

        produto.setNome(dto.getNome());
        produto.setPreco(dto.getPreco());
        produto.setCategoria(categoria);

        Produto produtoGuardado = repository.save(produto);

        return converterParaDTO(produtoGuardado);
    }

    // 2. Listar todos os produtos
    public List<ProdutoResponseDTO> listarTodos() {

        return repository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    // métodoos especiais de listagem de produtos usando Query
    public List<ProdutoResponseDTO> buscarPorPrecoSuperior(Double preco) {
        return repository.buscarPorPrecoSuperior(preco).stream()
                .map(this::converterParaDTO).toList();
    }

    public List<ProdutoResponseDTO> buscarPorCategoriaJPQL(Long categoriaId) {
        return repository.buscarPorCategoria(categoriaId).stream()
                .map(this::converterParaDTO).toList();
    }

    // 3. Listar produtos com paginação e ordenação
    public Page<ProdutoResponseDTO> listarPaginado(
            int pagina,
            int tamanho,
            String campoOrdenacao,
            String direcao) {

        if (pagina < 0) {
            throw new IllegalArgumentException(
                    "O número da página não pode ser negativo."
            );
        }

        if (tamanho < 1 || tamanho > 100) {
            throw new IllegalArgumentException(
                    "O tamanho da página deve estar entre 1 e 100."
            );
        }

        if (!campoOrdenacao.equals("id")
                && !campoOrdenacao.equals("nome")
                && !campoOrdenacao.equals("preco")) {

            throw new IllegalArgumentException(
                    "Campo de ordenação inválido. Utilize id, nome ou preco."
            );
        }

        if (!direcao.equalsIgnoreCase("asc")
                && !direcao.equalsIgnoreCase("desc")) {

            throw new IllegalArgumentException(
                    "A direcção deve ser asc ou desc."
            );
        }

        Sort.Direction direcaoOrdenacao =
                Sort.Direction.fromString(direcao);

        Pageable pageable = PageRequest.of(
                pagina,
                tamanho,
                Sort.by(direcaoOrdenacao, campoOrdenacao)
        );

        return repository.findAll(pageable)
                .map(this::converterParaDTO);
    }

    // 4. Buscar um produto pelo ID
    public ProdutoResponseDTO buscaPorId(Long id) {

        Produto produto = repository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(
                        "O produto com o ID " + id + " não foi encontrado."
                ));

        return converterParaDTO(produto);
    }

    // 5. Actualizar um produto
    public ProdutoResponseDTO actualizarProduto(
            Long id,
            ProdutoRequestDTO dto) {

        Produto produto = repository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(
                        "O produto com o ID " + id + " não foi encontrado."
                ));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "A categoria com o ID " + dto.getCategoriaId()
                                + " não foi encontrada."
                ));

        produto.setNome(dto.getNome());
        produto.setPreco(dto.getPreco());
        produto.setCategoria(categoria);

        Produto produtoActualizado = repository.save(produto);

        return converterParaDTO(produtoActualizado);
    }

    // 6. Eliminar um produto
    public void eleminar(Long id) {

        if (!repository.existsById(id)) {
            throw new ProdutoNaoEncontradoException(
                    "O produto com o ID " + id + " não foi encontrado."
            );
        }

        repository.deleteById(id);
    }

    // 7. Converter uma entidade Produto num DTO de resposta

    private ProdutoResponseDTO converterParaDTO(Produto produto) {

        CategoriaResponseDTO categoriaDTO = null;

        if (produto.getCategoria() != null) {
            categoriaDTO = new CategoriaResponseDTO(
                    produto.getCategoria().getId(),
                    produto.getCategoria().getNome()
            );
        }

        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getPreco(),
                categoriaDTO
        );
    }

    // 8. Lista produtos por categoria

    public List<ProdutoResponseDTO> listarCategoris(Long categoriaId) {
        return repository.findByCategoria_Id(categoriaId)
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    // 9. Pesquisa produto por preço mais alto
    public List<ProdutoResponseDTO> ListarPorPrecoSuperior(Double preco) {
        return repository.findByPrecoGreaterThan(preco)
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    // 9. Pesquisa produto por Nome
    public List<ProdutoResponseDTO> ListarPorNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }
}
