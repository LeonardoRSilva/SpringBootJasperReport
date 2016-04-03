package br.fpu.guilherme.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import br.fpu.guilherme.utils.BaseEntity;

@Entity
@Table(name = "tb_itensMovimentacao")
public class ItensMovimentacaoEntity extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "produto_id", nullable = false)
	private ProdutoEntity produto;

	@ManyToOne
	@JoinColumn(name = "movimentacao_id", nullable = false)
	private MovimentacaoEntity movimentacao;
	
	@NotNull
	@Min(0)
	@Max(9999)
	@Column(name = "quantidade", nullable = false, precision = 10, scale = 2)
	private BigDecimal quantidade;

	@Column(name = "valor_unitario", nullable = false, precision = 10, scale = 2)
	private BigDecimal valorUnitario;
	
	@Column(name = "total_produto", nullable = false, precision = 10, scale = 2)
	private BigDecimal totalProduto;

	public ItensMovimentacaoEntity() {
		// TODO Auto-generated constructor stub
	}

	public ItensMovimentacaoEntity(ProdutoEntity produto, MovimentacaoEntity movimentacao, BigDecimal quantidade,
			BigDecimal valorUnitario) {
		super();
		this.produto = produto;
		this.movimentacao = movimentacao;
		this.quantidade = quantidade;
		this.valorUnitario = valorUnitario;
	}

	public ProdutoEntity getProduto() {
		return produto;
	}

	public void setProduto(ProdutoEntity produto) {
		this.produto = produto;
	}

	public MovimentacaoEntity getMovimentacao() {
		return movimentacao;
	}

	public void setMovimentacao(MovimentacaoEntity movimentacao) {
		this.movimentacao = movimentacao;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	
	

	public BigDecimal getTotalProduto() {
		return totalProduto;
	}

	public void setTotalProduto(BigDecimal totalProduto) {
		this.totalProduto = totalProduto;
	}

	public void setTipoItensMovimentacao(TipoItensMovimentacao itemMovimentacao,ProdutoEntity produto, BigDecimal totalProduto){
		this.setProduto(produto);
		this.setQuantidade(itemMovimentacao.getQuantidade());
		this.setValorUnitario(produto.getCustoMedio());
		this.setTotalProduto(totalProduto);
	}
}
