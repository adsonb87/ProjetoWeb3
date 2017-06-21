package br.com.pe.urbana.entidade;

public class EntidadeUsuario {
	
	private int id;
	
	private int usrId;
	
	private String nome;
	
	private String cpf;
	
	private String telefone;
	
	private String email;
	
	private EntidadeEndereco endereco;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUsrId() {
		return usrId;
	}

	public void setUsrId(int usrId) {
		this.usrId = usrId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
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

	public EntidadeEndereco getEndereco() {
		return endereco;
	}

	public void setEndereco(EntidadeEndereco endereco) {
		this.endereco = endereco;
	}
	
}
