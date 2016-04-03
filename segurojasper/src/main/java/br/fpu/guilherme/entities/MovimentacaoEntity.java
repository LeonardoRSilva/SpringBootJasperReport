package br.fpu.guilherme.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import br.fpu.guilherme.utils.BaseEntity;

@Entity
@Table(name = "tb_movimentacao")
public class MovimentacaoEntity extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;

	

	@NotNull(message = "Não pode estar em branco") // JSR 303 Validated ?
	@Past(message = "insira uma data valida") // JSR 303 Validated ?
	@DateTimeFormat(pattern = "dd/MM/YY")
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_movimentacao")
	private Date dataMovimentacao;

	@NotNull
	@Min(0)
	@Max(999999)
	@Column(name = "valorTotal", nullable = false, precision = 10, scale = 2)
	private BigDecimal valorTotal = BigDecimal.ZERO;

	@NotBlank
	@Column(nullable = false, length = 70)
	private String movimentacaoTipo;
	
	@OneToMany(mappedBy = "movimentacao")
	private List<ItensMovimentacaoEntity> itens = new ArrayList<>();

	public MovimentacaoEntity() {
		// TODO Auto-generated constructor stub
	}
	
	public MovimentacaoEntity(Date dataMovimentacao, BigDecimal valorTotal, String movimentacaoTipo,
			List<ItensMovimentacaoEntity> itens) {
		super();
		this.dataMovimentacao = dataMovimentacao;
		this.valorTotal = valorTotal;
		this.movimentacaoTipo = movimentacaoTipo;
		this.itens = itens;
	}

	public Date getDataMovimentacao() {
		return dataMovimentacao;
	}

	public void setDataMovimentacao(Date dataMovimentacao) {
		this.dataMovimentacao = dataMovimentacao;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getMovimentacaoTipo() {
		return movimentacaoTipo;
	}

	public void setMovimentacaoTipo(String movimentacaoTipo) {
		this.movimentacaoTipo = movimentacaoTipo;
	}

	public List<ItensMovimentacaoEntity> getItens() {
		return itens;
	}

	public void setItens(List<ItensMovimentacaoEntity> itens) {
		this.itens = itens;
	}

	
	
	

}
