package br.fpu.guilherme.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;


import br.fpu.guilherme.entities.ProdutoEntity;

public interface IProdutoRepository extends JpaRepository<ProdutoEntity, Long> {
	
	ProdutoEntity findBySku(String sku);
	
	@Query("SELECT p FROM ProdutoEntity p WHERE p.nome LIKE CONCAT('%',:nome,'%')")
	List<ProdutoEntity> findByNomeLike(@Param("nome") String nome);	
	
	@Query("SELECT p FROM ProdutoEntity p WHERE p.sku LIKE CONCAT('%',:sku,'%')")
	List<ProdutoEntity> findBySkuLike(@Param("sku") String sku);	
}
