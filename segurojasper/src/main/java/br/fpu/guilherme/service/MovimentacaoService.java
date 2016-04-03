package br.fpu.guilherme.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.fpu.guilherme.entities.MovimentacaoEntity;
import br.fpu.guilherme.repository.IMovimentacaoRepository;
import br.fpu.guilherme.utils.GenericService;

@Service
@Transactional
public class MovimentacaoService extends GenericService<MovimentacaoEntity, Long> {

	@Autowired
	IMovimentacaoRepository movimentacaoRepository;
	
	public List<MovimentacaoEntity> findMovimentacaoBetween(Date inicio, Date fim){
		return this.movimentacaoRepository.findByDataMovimentacaoBetween(inicio, fim);
	}
	
	public List<MovimentacaoEntity> findMovimentacaoTipoLike(String movimentacaoTipo){
		return this.movimentacaoRepository.findByMovimentacaoTipoLike(movimentacaoTipo);
	}
	
	/*public void adicionarItemVazio(MovimentacaoEntity movimentacao) {
		ProdutoEntity produto = new ProdutoEntity();
		produto.setQuantidade(new BigDecimal("1.00"));
		
		ItensMovimentacaoEntity item = new ItensMovimentacaoEntity();
		item.setProduto(produto);
		//item.setMovimentacao(movimentacao);
		movimentacao.getItens().add(0, item);
	}*/

	

}
