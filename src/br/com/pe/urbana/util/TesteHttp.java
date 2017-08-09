package br.com.pe.urbana.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class TesteHttp {

	public static void main(String[] args) {
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("C:/Users/andres/Desktop/ANDRE CARLOS BATISTA DA SILVA.json"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//CRIA O LEITOR, A PARTIR DO ARQUIVO
		JsonReader leitorDeObjeto = Json.createReader(br);
		//FAZ A LEITURA E RETORNA O OBJETO
		JsonObject json = leitorDeObjeto.readObject();

		HttpClient httpClient = HttpClients.createDefault(); 

		try{
		    HttpPost request = new HttpPost("https://oauth.itau.com.br/identity/connect/token");
			
		    request.addHeader("Content-Type", "application/x-www-form-urlencoded");
		    request.addHeader("scope", "readonly");
		    request.addHeader("grant_type", "client_credentials");
		    request.addHeader("client_id", "wF3uNeLfw4Or0");
		    request.addHeader("client_secret", "roiKd8GL4JhflnR5o5mJX4crVOy6ROqW5zdY69YpZHXd97YZd96jjiQ5dKkOiwM2yz4wK5-GQLsZs9dK_rlXZA2");
		    		    
		    HttpResponse response = httpClient.execute(request);

		    HttpEntity conteudo = response.getEntity();		    
		    String conteudoString = EntityUtils.toString(conteudo, "UTF-8");
		    System.out.println(conteudoString);    
			
		}catch (Exception ex) {
			System.out.println(ex.getMessage());
		} 
		
//		try {
//		    HttpPost request = new HttpPost("https://gerador-boletos.itau.com.br/router-gateway-app/public/codigo_barras/registro");
//		    
//		    StringEntity params = new StringEntity(json.toString());
//		    
//		    request.addHeader("Accept", "application/vnd.itau");
//		    request.addHeader("access_token", "782dd15c197949f683883434b9aee2af");
//		    request.addHeader("itau-chave", "9a6a013b-54df-49a5-bf99-f674761f5775");
//		    request.addHeader("identificador", "09759606000180");
//		    request.addHeader("Content-Type", "application/json");
//		    
//		    request.setEntity(params);
//		    
//		    HttpResponse response = httpClient.execute(request);
//
//		    HttpEntity conteudo = response.getEntity();		    
//		    String conteudoString = EntityUtils.toString(conteudo, "UTF-8");
//		    System.out.println(conteudoString);    
//		    
//		}catch (Exception ex) {
//			System.out.println(ex.getMessage());
//		} 
		
	}

}
