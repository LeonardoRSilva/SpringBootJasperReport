package br.fpu.guilherme.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fpu.guilherme.entities.ProdutoEntity;
import br.fpu.guilherme.repository.IProdutoRepository;
import br.fpu.guilherme.utils.GenericService;
@Service
@Transactional
public class ProdutoService extends GenericService<ProdutoEntity, Long> {
	@Autowired
	IProdutoRepository produtoRepository;
	
	public ProdutoEntity findSku(String sku){
		return this.produtoRepository.findBySku(sku);
	}
	
	public List<ProdutoEntity> findProdutoByNome(String nome){
		return this.produtoRepository.findByNomeLike(nome);
	}
	
	public List<ProdutoEntity> findProdutoBySkuLike(String sku){
		return this.produtoRepository.findBySkuLike(sku);
	}
}
