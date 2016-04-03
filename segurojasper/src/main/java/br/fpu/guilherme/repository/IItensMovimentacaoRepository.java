package br.fpu.guilherme.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.fpu.guilherme.entities.ItensMovimentacaoEntity;
import br.fpu.guilherme.entities.ProdutoEntity;

public interface IItensMovimentacaoRepository extends JpaRepository<ItensMovimentacaoEntity, Long> {

	ItensMovimentacaoEntity findByProduto(ProdutoEntity produto);
}
