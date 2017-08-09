package br.com.pe.urbana.boleto;

import java.io.BufferedReader;
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
import org.json.JSONArray;
import org.json.JSONObject;

public class RegistrarBoleto {
	
	public final static String autentica() throws ClientProtocolException, IOException {
		
		String accessToken = null;
		HttpClient cliente = HttpClients.createDefault();
		HttpPost request = new HttpPost("https://oauth.itau.com.br/identity/connect/token");
		
		String clientId = "wF3uNeLfw4Or0";
		String clientSecret = "roiKd8GL4JhflnR5o5mJX4crVOy6ROqW5zdY69YpZHXd97YZd96jjiQ5dKkOiwM2yz4wK5-GQLsZs9dK_rlXZA2";
		
		//CONVERTE PARA BASE 64
		String credentials = clientId + ":" + clientSecret;
		String headerValue = "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());
		
	    request.addHeader("Authorization", headerValue);
		request.addHeader("Content-Type", "application/x-www-form-urlencoded");
		
		List<NameValuePair> valores = new ArrayList<NameValuePair>();
		valores.add(new BasicNameValuePair("scope", "readonly"));
		valores.add(new BasicNameValuePair("grant_type", "client_credentials"));
		request.setEntity(new UrlEncodedFormEntity(valores));

		HttpResponse response = cliente.execute(request);
		
		int status = response.getStatusLine().getStatusCode();
		if (status == HttpStatus.SC_OK){
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
	
	public final static MsgResponse registrar(String token) throws ClientProtocolException, IOException {
		
		String conteudoString = "";
		MsgResponse msgResponse = null;
		
		//FAZ A LEITURA E RETORNA O OBJETO
		BufferedReader br = new BufferedReader(new FileReader("C:/Users/andres/Desktop/ANDRE CARLOS BATISTA DA SILVA.json"));
		JsonReader leitorDeObjeto = Json.createReader(br);
		JsonObject jsonBoleto = leitorDeObjeto.readObject();
		
		HttpClient cliente = HttpClients.createDefault();
		HttpPost request = new HttpPost("https://gerador-boletos.itau.com.br/router-gateway-app/public/codigo_barras/registro");
		
		StringEntity params = new StringEntity(jsonBoleto.toString());
		
		request.addHeader("Accept", "application/vnd.itau");
		request.addHeader("access_token", token) ;
		request.addHeader("itau-chave", "9a6a013b-54df-49a5-bf99-f674761f5775");
		request.addHeader("identificador", "09759606000180");
		request.addHeader("Content-Type", "application/json");
		request.setEntity(params);
		
		HttpResponse response = cliente.execute(request);
		
		//PEGANDO O CONTEUDO DO RESPONSE
		HttpEntity conteudo = response.getEntity();
		conteudoString = EntityUtils.toString(conteudo, "UTF-8");
		
		int status = response.getStatusLine().getStatusCode();
		
		if(status == HttpStatus.SC_OK) {
			
			msgResponse = new MsgResponse()
					.comMensagem("OK");
			
		} else if (status == HttpStatus.SC_UNPROCESSABLE_ENTITY) {
			
			//CONVERTE O CONTEUDO DO RESPONSE PARA O JSON 
			JSONObject json = new JSONObject(conteudoString);
			
			msgResponse = new MsgResponse()
					.comCodigo(json.getString("codigo"))
					.comMensagem(json.getString("mensagem"));
			
		} else if (status == HttpStatus.SC_BAD_REQUEST) {

			//CONVERTE O CONTEUDO DO RESPONSE PARA O JSON 
			JSONObject json = new JSONObject(conteudoString);
			JSONArray campos = (JSONArray) json.get("campos");
			
			msgResponse = new MsgResponse()
					.comCodigo(json.getString("codigo"))
					.comMensagem(json.getString("mensagem"));
			
			List<Campos> lCampos = new ArrayList<>();
			for (int i = 0; i < campos.length(); i++) {
            	JSONObject object = campos.getJSONObject(i);
                Campos campo = new Campos()
                		.comCampo(object.getString("campo"))
                		.comMensagem(object.getString("mensagem"))
                		.comValor(object.getString("valor"));
                lCampos.add(campo);
            }
			msgResponse.comCampos(lCampos);
			
		}
		
		return msgResponse;
	}
	
	public static void main(String[] args) {

		String token = "";
		MsgResponse msgResponse = null;
		
		try {
			token = autentica();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			msgResponse = registrar(token);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Token: " + token);
		System.out.println("\nResposta do Registro\n" + msgResponse.toString());
		
	}

}
