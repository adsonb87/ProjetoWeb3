package br.com.pe.urbana.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class Importados {

	public static void main(String[] args) {
		
		List<String> recebidos = null;
		List<String> cadastrados = null;
		
		try {
			recebidos = FileUtils.readLines(new File("C:/Users/andres/Desktop/Arquivos/recebidos.txt"));
			cadastrados = FileUtils.readLines(new File("C:/Users/andres/Desktop/Arquivos/importados.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		java.util.Collections.sort(recebidos);
		java.util.Collections.sort(cadastrados);
		
		System.out.println("Recebidos: " + recebidos.size());
		System.out.println("Importados: " + cadastrados.size());
		
		recebidos.removeAll(cadastrados);
		
		System.out.println("Diferença: " + recebidos.size());
		
		for(String aux: recebidos) {
			System.out.println(aux);
		}
		
	}

}
