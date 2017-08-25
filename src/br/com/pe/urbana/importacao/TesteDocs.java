package br.com.pe.urbana.importacao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.pe.urbana.entidade.EntidadeEndereco;
import br.com.pe.urbana.entidade.EntidadeUsuario;

public class TesteDocs {

	public static void main(String[] args) throws IOException {
	
		List<EntidadeUsuario> usuarios = gerarUsuario();
		
		ArquivoDocs users = new ArquivoDocs();
		String aux = "";
		
		try {
			aux = users.createFile(usuarios);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(aux);
		
	}

	private static List<EntidadeUsuario> gerarUsuario() {
		
		List<EntidadeUsuario> usuarios = new ArrayList<>();
		
		EntidadeEndereco endereco1 = new EntidadeEndereco();
		endereco1.setCep("50721020");
		endereco1.setLogradouro("RUA DOM JOSE PEREIRA ALVES");
		endereco1.setBairro("CORDEIRO");
		endereco1.setCidade("RECIFE");
		endereco1.setUf("PE");
		endereco1.setComplemento("CASA 01");
		endereco1.setNumero("171");
		
		EntidadeUsuario usuario1 = new EntidadeUsuario();
		usuario1.setId(123);
		usuario1.setCpf("05188436450");
		usuario1.setNome("ANDRE CARLOS BATISTA DA SILVA");
		usuario1.setDataNascimento("931130");
		usuario1.setNomeMae("VILMA BATISTA DA SILVA");
		usuario1.setTelefone("81995672235");
		usuario1.setEmail("ANDRE.CARLOS1993@HOTMAIL.COM");
		usuario1.setEndereco(endereco1);
		
		EntidadeEndereco endereco2 = new EntidadeEndereco();
		endereco2.setCep("53230230");
		endereco2.setLogradouro("RIO BRANCO");
		endereco2.setBairro("JARDIM BRASIL 1");
		endereco2.setCidade("OLINDA");
		endereco2.setUf("PE");
		endereco2.setComplemento("CASA");
		endereco2.setNumero("123");
		
		EntidadeUsuario usuario2 = new EntidadeUsuario();
		usuario2.setId(456);
		usuario2.setCpf("10145755401");
		usuario2.setNome("LUCAS LEONCIO");
		usuario2.setDataNascimento("930815");
		usuario2.setNomeMae("ILMA LEONCIO");
		usuario2.setTelefone("81987878787");
		usuario2.setEmail("LUCAS.LEONCIO@HOTMAIL.COM");
		usuario2.setEndereco(endereco2);
		
		usuarios.add(usuario1);
		usuarios.add(usuario2);
		
		return usuarios;
	}

}
