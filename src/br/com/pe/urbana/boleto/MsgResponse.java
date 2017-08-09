package br.com.pe.urbana.boleto;

import java.util.List;

public class MsgResponse {
	
	private String codigo;
	private String mensagem;
	private List<Campos> campos;
	
	public MsgResponse(){}
	
	public String getCodigo() {
		return codigo;
	}
	public MsgResponse comCodigo(String codigo) {
		this.codigo = codigo;
		return this;
	}
	public String getMensagem() {
		return mensagem;
	}
	public MsgResponse comMensagem(String mensagem) {
		this.mensagem = mensagem;
		return this;
	}
	public List<Campos> getCampos() {
		return campos;
	}
	public MsgResponse comCampos(List<Campos> campos) {
		this.campos = campos;
		return this;
	}

	@Override
	public String toString() {
		return "{\n cogido: " + codigo + "\n mensagem: " + mensagem + "\n campos: " + campos + "\n\n}";
	}

}