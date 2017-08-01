package br.com.pe.urbana.util;

import java.util.Date;

import br.com.pe.urbana.entidade.EntidadeCobranca;

public class Teste {

	public static void main(String[] args) {
	
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
				
		EntidadeCobranca cobranca = new EntidadeCobranca();
		cobranca.setDataVencimento(new Date());
				
		System.out.println(cobranca.getDataVencimentoFormatada());
		
	}

}
