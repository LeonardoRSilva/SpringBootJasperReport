package br.fpu.guilherme.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fpu.guilherme.entities.ItensMovimentacaoEntity;
import br.fpu.guilherme.entities.ProdutoEntity;
import br.fpu.guilherme.repository.IItensMovimentacaoRepository;
import br.fpu.guilherme.utils.GenericService;

@Service
@Transactional
public class ItensMovimentacaoService extends GenericService<ItensMovimentacaoEntity, Long> {

	@Autowired
	IItensMovimentacaoRepository itensRepository;
	
	public ItensMovimentacaoEntity findProduto(ProdutoEntity produto){
		return this.itensRepository.findByProduto(produto);
	}

}
