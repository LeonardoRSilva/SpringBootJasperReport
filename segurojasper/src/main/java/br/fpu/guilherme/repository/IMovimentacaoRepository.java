package br.fpu.guilherme.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.fpu.guilherme.entities.MovimentacaoEntity;

public interface IMovimentacaoRepository extends JpaRepository<MovimentacaoEntity, Long> {

	List<MovimentacaoEntity> findByDataMovimentacaoBetween(Date inicio, Date fim);	
	
	@Query("SELECT m FROM MovimentacaoEntity m WHERE m.movimentacaoTipo LIKE CONCAT('%',:movimentacaoTipo,'%')")
	List<MovimentacaoEntity> findByMovimentacaoTipoLike(@Param("movimentacaoTipo") String movimentacaoTipo);	
}
