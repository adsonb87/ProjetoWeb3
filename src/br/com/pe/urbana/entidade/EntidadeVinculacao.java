package br.com.pe.urbana.entidade;

public class EntidadeVinculacao {
	
	private int id;
	
	private int idUsuario;
	
	private EntidadeCartao cartao;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public EntidadeCartao getCartao() {
		return cartao;
	}

	public void setCartao(EntidadeCartao cartao) {
		this.cartao = cartao;
	}

}
