package br.com.pe.urbana.entidade;

public class EntidadeCartao {
	
	private int projeto;
	
	private int design;
	
	private int numeroExterno;
	
	private EntidadeUsuario usuario;

	public int getProjeto() {
		return projeto;
	}

	public void setProjeto(int projeto) {
		this.projeto = projeto;
	}

	public int getDesign() {
		return design;
	}

	public void setDesign(int tipo) {
		this.design = tipo;
	}

	public int getNumeroExterno() {
		return numeroExterno;
	}

	public void setNumeroExterno(int numeroExterno) {
		this.numeroExterno = numeroExterno;
	}

	public EntidadeUsuario getUsuario() {
		return usuario;
	}

	public void setUsuario(EntidadeUsuario usuario) {
		this.usuario = usuario;
	}
	
}
