package br.com.pe.urbana.util;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.pe.urbana.entidade.EntidadeEndereco;
import br.com.pe.urbana.entidade.EntidadeUsuario;

public class EstudoJSON {

	public static void main(String[] args) throws JSONException {
		
		adicaoSimplesDeDados();
		
	}
	
	private static void adicaoSimplesDeDados() throws JSONException {

		EntidadeUsuario usuario = new EntidadeUsuario();
		EntidadeEndereco endereco = new EntidadeEndereco();

		usuario.setCpf("051.884.364-50");
		usuario.setNome("ANDRE CARLOS BATISTA DA SILVA");
		endereco.setCep("50721-020");
		endereco.setLogradouro("RUA DOM JOSE PERbEIRA ALVES");
		endereco.setBairro("CORDEIRO");
		endereco.setCidade("RECIFE");
		endereco.setUf("PE");
		endereco.setComplemento("CASA 01");
		endereco.setNumero("171");
		usuario.setEndereco(endereco);
		
		JSONObject usuarioJSON = new JSONObject();

		usuarioJSON.put("usuario", usuario);
		
		System.out.println(usuarioJSON);
	
	}
	
}