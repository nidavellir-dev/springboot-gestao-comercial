package com.davidandredaniel.springbootaula01.repository;


import com.davidandredaniel.springbootaula01.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByCategoria_Id(Long categoriaId);

    List<Produto> findByPrecoGreaterThan(Double preco);

    List<Produto> findByNomeContainingIgnoreCase(String nome);

    @Query("Select p from Produto p where p.preco>:preco")
    List<Produto> buscarPorPrecoSuperior(@Param("preco") Double preco);

    @Query("SELECT p FROM Produto p WHERE p.categoria.id = :categoriaId")
    List<Produto> buscarPorCategoria(@Param("categoriaId") Long categoriaId);

}


//  private Long proximoID = 1L;
//
//  // método para criar produtos
//  public Produto savar(Produto produto){
//      produto.setId(proximoID);
//      proximoID++;
//      produtos.add(produto);
//      return produto;
//  }
/// / método para listar produtos
//  public List<Produto> listarProdutos() {
//    return produtos;
//  }
//
/// /método para buscar por Id um produto éspecífico
//public Produto buscarPorId(Long Id){
//
//      for (Produto produto: produtos){
//          if (produto.getId().equals(Id)){
//              return produto;
//          }
//      }
//      return null;
//}
//    //método para acltualizar um produto éspecífico
//public Produto actualizar(Long id, Produto produtoActualizado){
//      Produto produto = buscarPorId(id);
//      if (produto != null){
//          produto.setNome(produtoActualizado.getNome());
//          produto.setPreco(produtoActualizado.getPreco());
//      }
//      return produto;
//}
/// /método para eliminar um produto éspecífico
//public boolean eliminar(Long id){
//    Produto produto = buscarPorId(id);
//    if (produto == null){
//        return false;
//    }
//    produtos.remove(produto);
//    return true;
//}
/// /método para fazer busaca por nome
//
//    public Produto buscarPorNome(String nome) {
//        Produto produto = new Produto();
//        if (produto.getNome().equalsIgnoreCase(nome)) {
//            return produto;
//        }
//        return null;
//    }


