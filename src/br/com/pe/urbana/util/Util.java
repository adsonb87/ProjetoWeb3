package br.com.pe.urbana.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class Util {
	
	public static final String PATH = "/usr/share/tomcat/webapps/teste/WEB-INF/classes/br/com/pe/urbana/log4j/log4j.properties";
//	public static final String PATH = "/usr/share/tomcat/vemcomum/WEB-INF/classes/br/com/pe/urbana/log4j/log4j.properties";
//	public static final String PATH = "//C:/projetos/vemcomum/src/br/com/pe/urbana/log4j/log4j.properties";
	
	public static String like(String nome) {

		String aux = "";
		aux = '%' + nome + '%';

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

	public static String unMaskCep(String cep) {
		
		if(cep != null && !cep.equals("")){
			cep = cep.replace("-", "");
		}
		return cep;
	}
	
	public static String getCrdSnr(String numeroCartao) {
		
		String crdSnr = null;
		
		if(numeroCartao != null && !numeroCartao.equals("")){			
			String[] text2 = numeroCartao.split(Pattern.quote("-"));
			crdSnr = text2[0];		
		}
		return crdSnr;
	}
	
	public static String unMaskTelefone(String telefone) {
		
		if(telefone != null && !telefone.equals("")) {
			telefone = telefone.replace("(", "");
			telefone = telefone.replace(")", "");
		}
		
		return telefone;
	}
	
	public static String formatDate(Date data) {
		
		String dataFormat = "";
		
		if (data != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			dataFormat = sdf.format(data);
		}
		
		return dataFormat;
	}
	
	public static String formatDate(Calendar data) {
		
		String dataFormat = "";
		
		if(data != null) {
			Date dt = new Date();		
			dt = data.getTime();
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			dataFormat = sdf.format(dt);
		}
		
		return dataFormat;
	}
	
	public static String formatDataNascimento(Date data) {
		
		String dataFormat = "";
		
		if (data != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			dataFormat = sdf.format(data);
		}
		
		return dataFormat;
	}
	
	public static String formatDataNascimento(Calendar data) {
		
		String dataFormat = "";
		
		if(data != null) {
			Date dt = new Date();		
			dt = data.getTime();
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			dataFormat = sdf.format(dt);
		}
		
		return dataFormat;
	}
	
	public static String formatTelefone(String telefone) {
		
		if(telefone != null && !telefone.equals("")) {
			return "(" + telefone.substring(0, 2) + ")" + telefone.substring(2);
		}
		
		return "";
	}
	
}
