package br.com.pe.urbana.boleto;

public class Moeda {
	
	private String sigla_moeda;
	private Integer quantidade_moeda;
	private Integer cotacao_moeda;

	public Moeda() {}

	public String getSigla_moeda() {
		return sigla_moeda;
	}

	public void setSigla_moeda(String sigla_moeda) {
		this.sigla_moeda = sigla_moeda;
	}

	public Integer getQuantidade_moeda() {
		return quantidade_moeda;
	}

	public void setQuantidade_moeda(Integer quantidade_moeda) {
		this.quantidade_moeda = quantidade_moeda;
	}

	public Integer getCotacao_moeda() {
		return cotacao_moeda;
	}

	public void setCotacao_moeda(Integer cotacao_moeda) {
		this.cotacao_moeda = cotacao_moeda;
	}

}
