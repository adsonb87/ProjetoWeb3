package br.com.pe.urbana.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import br.com.caelum.stella.boleto.Beneficiario;
import br.com.caelum.stella.boleto.Boleto;
import br.com.caelum.stella.boleto.Endereco;
import br.com.caelum.stella.boleto.Pagador;

public class LerJson {

	public static void main(String[] args) throws FileNotFoundException {

//		BufferedReader br = new BufferedReader(new FileReader("C:/Users/andres/Desktop/ANDRE CARLOS BATISTA DA SILVA.json"));
//		
//		JsonReader leitorDeObjeto = Json.createReader(br);
//        
//		//FAZ A LEITURA E RETORNA O NOSSO OBJETO
//		JsonObject json = leitorDeObjeto.readObject();
//		         
//		//CONSTRUINDO UM BOLETO, COM AS INFORMAÇÕES DO JSON
//		
//		Beneficiario beneficiario = Beneficiario.novoBeneficiario()
//				.comDocumento(json.getJsonObject("beneficiario").getString("cpf_cnpj_beneficiario"))
//				.comAgencia(json.getJsonObject("beneficiario").getString("agencia_beneficiario"))
//				.comCodigoBeneficiario(json.getJsonObject("beneficiario").getString("conta_beneficiario"))
//				.comDigitoCodigoBeneficiario(json.getJsonObject("beneficiario").getString("digito_verificador_conta_beneficiario"));
//		
//		Endereco enderecoPagador = Endereco.novoEndereco()
//				.comLogradouro(json.getJsonObject("pagador").getString("logradouro_pagador"))
//				.comBairro(json.getJsonObject("pagador").getString("bairro_pagador"))
//				.comCidade(json.getJsonObject("pagador").getString("cidade_pagador"))
//				.comUf(json.getJsonObject("pagador").getString("uf_pagador"))
//				.comCep(json.getJsonObject("pagador").getString("cep_pagador"));
//		
//		Pagador pagador = Pagador.novoPagador()
//				.comDocumento(json.getJsonObject("pagador").getString("cpf_cnpj_pagador"))
//				.comNome(json.getJsonObject("pagador").getString("nome_pagador"))
//				.comEndereco(enderecoPagador);
//		
//		Boleto boleto = Boleto.novoBoleto()
//				.comTipoAmbiente(json.getInt("tipo_ambiente"))
//				.comTipoRegistro(json.getInt("tipo_registro"))
//				.comTipoCobranca(json.getInt("tipo_cobranca"))
//				.comTipoProduto(json.getString("tipo_produto"))
//				.comSubProduto(json.getString("subproduto"))
//				.comBeneficiario(beneficiario)
//				.comAceite(Boolean.parseBoolean(json.getString("titulo_aceite")))
//				.comPagador(pagador);
//		
//		System.out.println(json.toString());
		
	}

}
