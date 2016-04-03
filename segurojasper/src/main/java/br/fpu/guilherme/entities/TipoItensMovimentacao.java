package br.fpu.guilherme.entities;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class TipoItensMovimentacao {

	 @Valid
	 @NotNull
	private ProdutoEntity produto;
	
	@NotNull
	@DecimalMin("1.00")
	@DecimalMax("1000000.00")
	private BigDecimal quantidade;

	public TipoItensMovimentacao() {
		
	}

	public ProdutoEntity getProduto() {
		return produto;
	}

	public void setProduto(ProdutoEntity produto) {
		this.produto = produto;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

}
