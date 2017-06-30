package br.com.pe.urbana.entidade;

public class EntidadeVinculacao {
	
	private int id;
	
	private int idUsuario;
	
	private int usrId;
	
	private EntidadeCartao cartao;
	
	private String regDate;

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

	public int getUsrId() {
		return usrId;
	}

	public void setUsrId(int usrId) {
		this.usrId = usrId;
	}

	public EntidadeCartao getCartao() {
		return cartao;
	}

	public void setCartao(EntidadeCartao cartao) {
		this.cartao = cartao;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	
}
