package br.com.pe.urbana.boleto;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.google.gson.Gson;

public class TesteRegistraBoleto {
	
	private static String autentica() throws ClientProtocolException, IOException {
		
		String accessToken = null;
		String url = "https://oauth.itau.com.br/identity/connect/token";
		String clientId = "wF3uNeLfw4Or0";
		String clientSecret = "roiKd8GL4JhflnR5o5mJX4crVOy6ROqW5zdY69YpZHXd97YZd96jjiQ5dKkOiwM2yz4wK5-GQLsZs9dK_rlXZA2";
		String contentType = "application/x-www-form-urlencoded";
		String scope = "readonly";
		String grantType = "client_credentials";
		
		//CONVERTE PARA BASE 64
		String credentials = clientId + ":" + clientSecret;
		String headerValue = "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());
		
		HttpClient cliente = HttpClients.createDefault();
		HttpPost request = new HttpPost(url);

	    request.addHeader("Authorization", headerValue);
		request.addHeader("Content-Type", contentType);
		
		List<NameValuePair> valores = new ArrayList<NameValuePair>();
		valores.add(new BasicNameValuePair("scope", scope));
		valores.add(new BasicNameValuePair("grant_type", grantType));
		request.setEntity(new UrlEncodedFormEntity(valores));

		HttpResponse response = cliente.execute(request);
		
		int status = response.getStatusLine().getStatusCode();
		if (status == HttpStatus.SC_OK) {
			//PEGANDO O CONTEUDO DO RESPONSE
			HttpEntity conteudo = response.getEntity();
			String conteudoString = EntityUtils.toString(conteudo, "UTF-8");

			//CONVERTE O CONTEUDO DO RESPONSE PARA O JSON 
			JSONObject json = new JSONObject(conteudoString);
			accessToken = json.getString("access_token");
		} else {
			System.out.println("Deu erro!");
		}
			
		return accessToken;
	}
	
	public static RetornoRegistro registrar(JsonObject jsonBoleto) throws ClientProtocolException, IOException {
		
		String token = autentica();
		RetornoRegistro retornoRegistro = null;

		if(token != null) {
			
			String url = "https://gerador-boletos.itau.com.br/router-gateway-app/public/codigo_barras/registro";
			String accept = "application/vnd.itau";
			String itauChave = "9a6a013b-54df-49a5-bf99-f674761f5775";
			String contentType = "application/json";
			String identificador = "09759606000180";
			
			HttpClient cliente = HttpClients.createDefault();
			HttpPost request = new HttpPost(url);
			
			StringEntity params = new StringEntity(jsonBoleto.toString());
			
			request.addHeader("Accept", accept);
			request.addHeader("access_token", token);
			request.addHeader("itau-chave", itauChave);
			request.addHeader("Content-Type", contentType);
			request.addHeader("identificador", identificador);
			request.setEntity(params);
			
			HttpResponse response = cliente.execute(request);
			
			int status = response.getStatusLine().getStatusCode();
			if(status == HttpStatus.SC_OK) {
				
				retornoRegistro = new RetornoRegistro();
				retornoRegistro.setMensagem("OK");
				retornoRegistro.setStatus(status);
				
			} else {
				
				//PEGANDO O CONTEUDO DO RESPONSE
				HttpEntity conteudo = response.getEntity();
				String conteudoString = EntityUtils.toString(conteudo, "UTF-8");
				
				//USADO PARA CONVERTER O CONTEUDO DO RESPONSE
				Gson gson = new Gson();
				
				//CONVERTE JSON PARA CLASS
				retornoRegistro = gson.fromJson(conteudoString, RetornoRegistro.class);
				retornoRegistro.setStatus(status);
			}			
		}
		
		return retornoRegistro;
	}
	
	public static void main(String[] args) {

		RetornoRegistro retornoRegistro = null;

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("C:/Users/andres/Desktop/ANDRE CARLOS BATISTA DA SILVA.json"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		//FAZ A LEITURA E RETORNA O OBJETO
		JsonReader leitorDeObjeto = Json.createReader(br);
		JsonObject jsonBoleto = leitorDeObjeto.readObject();
		
		try {
			retornoRegistro = registrar(jsonBoleto);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(retornoRegistro.toString());
		
	}

}