package br.fpu.guilherme.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import br.fpu.guilherme.utils.BaseEntity;

@Entity
@Table(name = "tb_produto")
public class ProdutoEntity extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;

	@NotBlank
	@Size(max = 80)
	@Column(nullable = false, length = 80)
	private String nome;

	@NotBlank
	@Column(nullable = false, length = 20, unique = true)
	private String sku;

	@NotNull
	@Column(name = "custo_medio", nullable = false, precision = 10, scale = 2)
	private BigDecimal custoMedio;

	@NotBlank
	@Size(max = 80)
	@Column(nullable = false, length = 80)
	private String fabricante;

	public ProdutoEntity() {

	}

	public ProdutoEntity(String nome, String sku, BigDecimal custoMedio, String fabricante) {
		super();
		this.nome = nome;
		this.sku = sku;
		this.custoMedio = custoMedio;
		this.fabricante = fabricante;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public BigDecimal getCustoMedio() {
		return custoMedio;
	}

	public void setCustoMedio(BigDecimal custoMedio) {
		this.custoMedio = custoMedio;
	}

	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

}
