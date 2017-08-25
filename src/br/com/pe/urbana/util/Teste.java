package br.com.pe.urbana.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;

import com.google.gson.Gson;

import br.com.pe.urbana.boleto.RetornoRegistro;

public class Teste {

	public static void main(String[] args) throws ParseException, FileNotFoundException {
	
//		GeradorDeDigitoPadrao geradorDV = new GeradorDeDigitoPadrao();
//		String bloco = "2938" + "28439" + "157" + "00000004";
//		int dv = geradorDV.geraDigitoBloco1(bloco);
//		System.out.println(dv);
		
		BufferedReader br = new BufferedReader(new FileReader("C:/Users/andres/Desktop/retornoBanco.json"));
		
		Gson gson = new Gson();
		
		RetornoRegistro retorno = gson.fromJson(br, RetornoRegistro.class);
		
		System.out.println(retorno);
		
	}

}
