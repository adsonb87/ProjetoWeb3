package br.com.pe.urbana.entidade;

import br.com.caelum.stella.format.CPFFormatter;

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
	
	private EnderecoUsuario endereco;

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

	public EnderecoUsuario getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoUsuario endereco) {
		this.endereco = endereco;
	}

	@Override
	public String toString() {
		return "[cpf=" + cpf + ", nome=" + nome + ", cep=" + endereco.getCep() + ", logradouro=" + endereco.getLogradouro() 
			+ ", bairro=" + endereco.getBairro() + ", cidade=" + endereco.getCidade() + ", numero=" + endereco.getNumero() 
			+ ", uf=" + endereco.getUf() + ", complemento=" + endereco.getComplemento() +"]";
	}
	
	public String getCpfFormatado() {
		return new CPFFormatter().format(cpf);
	}
		
}
