package br.com.pe.urbana.boleto;

public class Beneficiario {
	
	private String codigo_banco_beneficiario;
	private String digito_verificador_banco_beneficiario;
	private String agencia_beneficiario;
	private String conta_beneficiario;
	private String digito_verificador_conta_beneficiario;
	private String cpf_cnpj_beneficiario;
	private String nome_razao_social_beneficiario;
	private String logradouro_beneficiario;
	private String bairro_beneficiario;
	private String complemento_beneficiario;
	private String cidade_beneficiario;
	private String uf_beneficiario;
	private String cep_beneficiario;
	
	public Beneficiario() {}

	public String getCodigo_banco_beneficiario() {
		return codigo_banco_beneficiario;
	}

	public void setCodigo_banco_beneficiario(String codigo_banco_beneficiario) {
		this.codigo_banco_beneficiario = codigo_banco_beneficiario;
	}

	public String getDigito_verificador_banco_beneficiario() {
		return digito_verificador_banco_beneficiario;
	}

	public void setDigito_verificador_banco_beneficiario(String digito_verificador_banco_beneficiario) {
		this.digito_verificador_banco_beneficiario = digito_verificador_banco_beneficiario;
	}

	public String getAgencia_beneficiario() {
		return agencia_beneficiario;
	}

	public void setAgencia_beneficiario(String agencia_beneficiario) {
		this.agencia_beneficiario = agencia_beneficiario;
	}

	public String getConta_beneficiario() {
		return conta_beneficiario;
	}

	public void setConta_beneficiario(String conta_beneficiario) {
		this.conta_beneficiario = conta_beneficiario;
	}

	public String getDigito_verificador_conta_beneficiario() {
		return digito_verificador_conta_beneficiario;
	}

	public void setDigito_verificador_conta_beneficiario(String digito_verificador_conta_beneficiario) {
		this.digito_verificador_conta_beneficiario = digito_verificador_conta_beneficiario;
	}

	public String getCpf_cnpj_beneficiario() {
		return cpf_cnpj_beneficiario;
	}

	public void setCpf_cnpj_beneficiario(String cpf_cnpj_beneficiario) {
		this.cpf_cnpj_beneficiario = cpf_cnpj_beneficiario;
	}

	public String getNome_razao_social_beneficiario() {
		return nome_razao_social_beneficiario;
	}

	public void setNome_razao_social_beneficiario(String nome_razao_social_beneficiario) {
		this.nome_razao_social_beneficiario = nome_razao_social_beneficiario;
	}

	public String getLogradouro_beneficiario() {
		return logradouro_beneficiario;
	}

	public void setLogradouro_beneficiario(String logradouro_beneficiario) {
		this.logradouro_beneficiario = logradouro_beneficiario;
	}

	public String getBairro_beneficiario() {
		return bairro_beneficiario;
	}

	public void setBairro_beneficiario(String bairro_beneficiario) {
		this.bairro_beneficiario = bairro_beneficiario;
	}

	public String getComplemento_beneficiario() {
		return complemento_beneficiario;
	}

	public void setComplemento_beneficiario(String complemento_beneficiario) {
		this.complemento_beneficiario = complemento_beneficiario;
	}

	public String getCidade_beneficiario() {
		return cidade_beneficiario;
	}

	public void setCidade_beneficiario(String cidade_beneficiario) {
		this.cidade_beneficiario = cidade_beneficiario;
	}

	public String getUf_beneficiario() {
		return uf_beneficiario;
	}

	public void setUf_beneficiario(String uf_beneficiario) {
		this.uf_beneficiario = uf_beneficiario;
	}

	public String getCep_beneficiario() {
		return cep_beneficiario;
	}

	public void setCep_beneficiario(String cep_beneficiario) {
		this.cep_beneficiario = cep_beneficiario;
	}

}
