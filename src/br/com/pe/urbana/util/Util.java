package br.com.pe.urbana.util;

import java.util.regex.Pattern;

public class Util {

	public static final String PATH = "/usr/share/tomcat/webapps/VemFinanceiro/WEB-INF/classes/br/com/pe/urbana/log4j/log4j.properties";
	
	public static String like(String nome) {

		String aux = "";
		aux = '%' + nome + '%';

		return aux;
	}

	public static String mmYYYY(String competencia) {
		String aux = null;

		if (competencia != null && !competencia.equals("")) {

			aux = competencia.substring(4, 6) + "/"
					+ competencia.substring(0, 4);
		}

		return aux;
	}

	public static String yyyyMM(String competencia) {
		String aux = null;
		if (competencia != null && !competencia.equals("")) {

			aux = competencia.substring(3, 7) + competencia.substring(0, 2);
		}
		return aux;
	}
	
	public static String realDouble (String vlr){
		String aux = null;
		
		if(vlr != null && !vlr.equals("")){
			aux = vlr.replace(".", "/");
			aux = aux.replace(",", ".");
			aux = aux.replace("/", "");
			
		}
		
		return aux;
	}
	
	public static String realDoubleRS (String vlr){
		String aux = null;
		
		if(vlr != null && !vlr.equals("")){
			vlr = vlr.substring(2,vlr.length());
			aux = vlr.replace(".", "/");
			aux = aux.replace(",", ".");
			aux = aux.replace("/", "");
			
		}
		
		return aux;
	}
	
	public static String unMaskCnpj(String cnpj) {
		
		if(cnpj != null && !cnpj.equals("")){
			cnpj = cnpj.replace(".", "");
			cnpj = cnpj.replace(".", "");
			cnpj = cnpj.replace("/", "");
			cnpj = cnpj.replace("-", "");
		}
		return cnpj;
	}
	
	public static String getCrdSnr(String numeroCartao) {
		
		String crdSnr = null;
		
		if(numeroCartao != null && !numeroCartao.equals("")){			
			String[] text2 = numeroCartao.split(Pattern.quote("-"));
			crdSnr = text2[0];		
		}
		return crdSnr;
	}
	
	public static String getTelefone(String telefone) {
		
		if(telefone != null && !telefone.equals("")) {
			telefone = telefone.replace("(", "");
			telefone = telefone.replace(")", "");
		}
		
		return telefone;
	}
	
}
