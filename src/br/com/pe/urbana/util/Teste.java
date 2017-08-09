package br.com.pe.urbana.util;

import java.text.ParseException;
import java.util.Base64;

public class Teste {

	public static void main(String[] args) throws ParseException {
	
//		Date data = new Date();
//		
//		Calendar c = Calendar.getInstance();
//		c.set(1993, 10, 30);
//		
//		data = c.getTime();
//		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//		String dtString = sdf.format(data);
//		
//		System.out.println(dtString);
//		
//		Calendar cdt = Calendar.getInstance();
//		cdt.add(Calendar.DAY_OF_MONTH, +5);
//		System.out.println(cdt.getTime());
				
//		EntidadeCobranca cobranca = new EntidadeCobranca();
//		cobranca.setDataVencimento(new Date());
//				
//		System.out.println(cobranca.getDataVencimentoFormatada());

//		EntidadeUsuario usuario = new EntidadeUsuario();
//		EntidadeEndereco endereco = new EntidadeEndereco();
//		
//		usuario.setNome("TESTE");
//		usuario.setTelefone("81987878787");
//		endereco.setLogradouro("rua dom jose");
//		endereco.setNumero("123");
//		endereco.setBairro("cordeiro");
//		endereco.setCidade("Recife");
//		endereco.setUf("PE");
//		usuario.setEndereco(endereco);
//		
//		//System.out.println(usuario.getEnderecoFormatado());
//				
//		System.out.println(usuario.getTelefoneFormatado());
		
		byte[] encodedBytes = Base64.getEncoder().encode("Teste".getBytes());
		System.out.println("encodedBytes " + new String(encodedBytes));
		byte[] decodedBytes = Base64.getDecoder().decode(encodedBytes);
		System.out.println("decodedBytes " + new String(decodedBytes));
		
	}

}
