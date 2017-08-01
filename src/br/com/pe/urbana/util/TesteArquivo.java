package br.com.pe.urbana.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

public class TesteArquivo {
	
	
	public static void main(String[] args) {

		//javaJson();
		jsonJava();
		
	}
	
	private static void javaJson() {
		
		Gson gson = new Gson();
		 
		// CONVERTE OBJETO JAVA PARA JSON E RETORNA JSON COMO STRING
		String json = gson.toJson("");
	 
		try {
			FileWriter writer = new FileWriter("C:\\boleto.json");
			writer.write(json);
			writer.close();
	 
		} catch (IOException e) {
			e.printStackTrace();
		}
	 
		System.out.println(json);
		
	}
	
	private static void jsonJava() {
		
		Gson gson = new Gson();

		try {

			BufferedReader br = new BufferedReader(new FileReader("c:\\boleto.json"));

			//Converte String JSON para objeto Java
			//ArquivoBanco obj = gson.fromJson(br, ArquivoBanco.class);

			//System.out.println(obj);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
