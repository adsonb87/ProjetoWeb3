package br.com.pe.urbana.entidade;

public enum Status {
	
	PENDENTE(1),
	REGISTRADO(2),
	PAGO(3),
	GERADO(4);
	
	private final int valor;
	
	Status(int status){
		valor = status;
	}
	
	public int getValor(){
		return valor;	
	}
	
	public static Status parse(Integer id) {
		
		Status status = null;
		
		if(id == 1) status = Status.PENDENTE;
		else if(id == 2) status = Status.REGISTRADO;
		else if(id == 3) status = Status.PAGO;
		else if(id == 4) status = Status.GERADO;
		
		return status;
	}
	
}
