package br.com.pe.urbana.entidade;

import br.com.caelum.stella.format.CPFFormatter;
import br.com.pe.urbana.util.Util;

public class EntidadeUsuario extends Entidade{
	
	private int id;
		
	private int usrIdOrigem;
	
	private String cpf;

	private String nome;
	
	private String dataNascimento;
	
	private String nomeMae;
	
	private String telefone;
	
	private String email;
	
	private EntidadeCartao cartao;
	
	private EntidadeEndereco endereco;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUsrIdOrigem() {
		return usrIdOrigem;
	}

	public void setUsrIdOrigem(int usrIdOrigem) {
		this.usrIdOrigem = usrIdOrigem;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public EntidadeCartao getCartao() {
		return cartao;
	}

	public void setCartao(EntidadeCartao cartao) {
		this.cartao = cartao;
	}

	public EntidadeEndereco getEndereco() {
		return endereco;
	}

	public void setEndereco(EntidadeEndereco endereco) {
		this.endereco = endereco;
	}
	
	public String getEnderecoFormatado() {
		return endereco.getLogradouro().concat(", Nº").concat(endereco.getNumero()).concat(" ")
				.concat(endereco.getBairro()).concat(" - ").concat(endereco.getCidade())
				.concat("/").concat(endereco.getUf());
	}
	
	public String getCpfFormatado() {
		return new CPFFormatter().format(cpf);
	}
	
	public String getTelefoneFormatado() {
		return Util.formatTelefone(telefone);
	}
		
}
