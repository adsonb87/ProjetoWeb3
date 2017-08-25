package br.com.pe.urbana.entidade;

import java.math.BigDecimal;
import java.util.Date;

import br.com.caelum.stella.format.CPFFormatter;
import br.com.pe.urbana.util.Util;

public class EntidadeCobranca {
	
	private Integer id;
	private String cpfPagador;
	private String nome;
	private Integer nossoNumero;
	private Date dataVencimento;
	private Date dataProcessamento;
	private Status status;
	private BigDecimal valor;
	private Date dataPagamento;
	private Date dataCredito;
	private String regUser;
	private Date regDate;
	
	public EntidadeCobranca() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCpfPagador() {
		return cpfPagador;
	}

	public void setCpfPagador(String cpfPagador) {
		this.cpfPagador = cpfPagador;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getNossoNumero() {
		return nossoNumero;
	}

	public void setNossoNumero(Integer nossoNumero) {
		this.nossoNumero = nossoNumero;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Date getDataProcessamento() {
		return dataProcessamento;
	}

	public void setDataProcessamento(Date dataProcessamento) {
		this.dataProcessamento = dataProcessamento;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public Date getDataCredito() {
		return dataCredito;
	}

	public void setDataCredito(Date dataCredito) {
		this.dataCredito = dataCredito;
	}

	public String getRegUser() {
		return regUser;
	}

	public void setRegUser(String regUser) {
		this.regUser = regUser;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public String getCpfFormatado() {
		return new CPFFormatter().format(cpfPagador);
	}
	
	public String getDataVencimentoFormatada() {
		return Util.formatDataNascimento(dataVencimento);
	}
	
	public String getDataEmissaoFormatada() {
		return Util.formatDataNascimento(dataProcessamento);
	}
	
	public String getDataPagamentoFormatada() {
		return Util.formatDataNascimento(dataPagamento);
	}
	
	public String getValorFormatado() {
		return String.format("R$ %.2f", valor);
	}
	
	public String getNossoNumeroFormatado() {
		return String.format("%08d", nossoNumero);
	}
	
}
