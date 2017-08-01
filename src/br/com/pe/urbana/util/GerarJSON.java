package br.com.pe.urbana.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

import br.com.pe.urbana.entidade.EnderecoUsuario;
import br.com.pe.urbana.entidade.EntidadeUsuario;

public class GerarJSON {

	public static void main(String[] args) {

		//javaJson();
		jsonJava();
		
	}
	
	private static void javaJson() {
		EntidadeUsuario usuario = new EntidadeUsuario();
		EnderecoUsuario endereco = new EnderecoUsuario();

		usuario.setCpf("051.884.364-50");
		usuario.setNome("ANDRE CARLOS BATISTA DA SILVA");
		endereco.setCep("50721-020");
		endereco.setLogradouro("RUA DOM JOSE PEREIRA ALVES");
		endereco.setBairro("CORDEIRO");
		endereco.setCidade("RECIFE");
		endereco.setUf("PE");
		endereco.setComplemento("CASA 01");
		endereco.setNumero("171");
		usuario.setEndereco(endereco);
		
		Gson gson = new Gson();
	 
		// CONVERTE OBJETO JAVA PARA JSON E RETORNA JSON COMO STRING
		String json = gson.toJson(usuario);
	 
		try {
			FileWriter writer = new FileWriter("C:\\usuario.json");
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

			BufferedReader br = new BufferedReader(new FileReader("c:\\usuario.json"));

			//Converte String JSON para objeto Java
			EntidadeUsuario obj = gson.fromJson(br, EntidadeUsuario.class);

			System.out.println(obj);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
