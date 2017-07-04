package br.com.pe.urbana.util;

import java.util.Calendar;
import java.util.Date;

public class Teste {

	public static void main(String[] args) {

		Calendar c = Calendar.getInstance();
		
		c.set(1993, 11, 30);
		Date data = c.getTime();
		System.out.println("Data atual sem formatação: "+data);
		
	}

}
