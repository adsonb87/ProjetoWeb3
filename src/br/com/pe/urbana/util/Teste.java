package br.com.pe.urbana.util;

public class Teste {

	public static void main(String[] args) {
		
		String text = "53290-270";
		String cep = text.replaceAll("-", "");
		System.out.println(cep);
	}

}
