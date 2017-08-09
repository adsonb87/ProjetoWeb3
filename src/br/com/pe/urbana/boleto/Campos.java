package br.com.pe.urbana.boleto;

public class Campos {
	
	private String campo;
	private String mensagem;
	private String valor;
	
	public Campos(){}
	
	public String getCampo() {
		return campo;
	}
	public Campos comCampo(String campo) {
		this.campo = campo;
		return this;
	}
	public String getMensagem() {
		return mensagem;
	}
	public Campos comMensagem(String mensagem) {
		this.mensagem = mensagem;
		return this;
	}
	public String getValor() {
		return valor;
	}
	public Campos comValor(String valor) {
		this.valor = valor;
		return this;
	}

	@Override
	public String toString() {
		return "\n   {\n      campo: " + campo + "\n      mensagem: " + mensagem + "\n      valor: " + valor + "\n   }\n ";
	}
	
}
