package br.com.pe.urbana.boleto;

public class Campos {
	
	private String campo;
	private String mensagem;
	private String valor;
	
	public Campos(){}

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "\n   {\n      campo: " + campo + "\n      mensagem: " + mensagem + "\n      valor: " + valor + "\n   }\n ";
	}
	
}
