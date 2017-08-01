package br.com.pe.urbana.entidade;

public enum Stt {
	
	PENDENTE(1),
	REGISTRADO(2),
	PAGO(3),
	GERADO(4);
	
	private final int valor;
	Stt(int status){
		valor = status;
	}
	
	public int getValor(){
		return valor;
	}

}
