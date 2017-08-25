package br.com.pe.urbana.boleto;

public class Pagador {

	private String cpf_cnpj_pagador;
	private String nome_razao_social_pagador;
	private String logradouro_pagador;
	private String complemento_pagador;
	private String bairro_pagador;
	private String cidade_pagador;
	private String uf_pagador;
	private String cep_pagador;
	
	public Pagador() {}

	public String getCpf_cnpj_pagador() {
		return cpf_cnpj_pagador;
	}

	public void setCpf_cnpj_pagador(String cpf_cnpj_pagador) {
		this.cpf_cnpj_pagador = cpf_cnpj_pagador;
	}

	public String getNome_razao_social_pagador() {
		return nome_razao_social_pagador;
	}

	public void setNome_razao_social_pagador(String nome_razao_social_pagador) {
		this.nome_razao_social_pagador = nome_razao_social_pagador;
	}

	public String getLogradouro_pagador() {
		return logradouro_pagador;
	}

	public void setLogradouro_pagador(String logradouro_pagador) {
		this.logradouro_pagador = logradouro_pagador;
	}

	public String getComplemento_pagador() {
		return complemento_pagador;
	}

	public void setComplemento_pagador(String complemento_pagador) {
		this.complemento_pagador = complemento_pagador;
	}

	public String getBairro_pagador() {
		return bairro_pagador;
	}

	public void setBairro_pagador(String bairro_pagador) {
		this.bairro_pagador = bairro_pagador;
	}

	public String getCidade_pagador() {
		return cidade_pagador;
	}

	public void setCidade_pagador(String cidade_pagador) {
		this.cidade_pagador = cidade_pagador;
	}

	public String getUf_pagador() {
		return uf_pagador;
	}

	public void setUf_pagador(String uf_pagador) {
		this.uf_pagador = uf_pagador;
	}

	public String getCep_pagador() {
		return cep_pagador;
	}

	public void setCep_pagador(String cep_pagador) {
		this.cep_pagador = cep_pagador;
	}
}
