package br.com.pe.urbana.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import br.com.pe.urbana.controller.rep.UsuarioControllerRep;
import br.com.pe.urbana.entidade.EntidadeUsuario;

public class UsuarioContoller {

	private static UsuarioContoller instance;

	public UsuarioContoller() {

	}

	public static UsuarioContoller getInstance() {

		if (instance == null) {
			instance = new UsuarioContoller();
		}

		return instance;
	}
	
	public boolean consultarUsuario(String cpf) throws ClassNotFoundException, 
		NamingException, SQLException {
		
		UsuarioControllerRep controllerRep = UsuarioControllerRep.getInstance();
		boolean flag = controllerRep.consultarUsuario(cpf);
		
		return flag;
	}
	
	public boolean consultarUsuarioNovo(String cpf) throws ClassNotFoundException, 
		NamingException, SQLException {
		
		UsuarioControllerRep controllerRep = UsuarioControllerRep.getInstance();
		boolean flag = controllerRep.consultarUsuarioNovo(cpf);
		
		return flag;
	}
	
	public EntidadeUsuario consultarCpf(String cpf) throws ClassNotFoundException, 
		NamingException, SQLException {
		
		EntidadeUsuario usuario = null;
		
		UsuarioControllerRep controllerRep = UsuarioControllerRep.getInstance();
		usuario = controllerRep.consultarCpf(cpf);
		
		return usuario;
		
	}
	
	public EntidadeUsuario consultarCpfNovo(String cpf) throws ClassNotFoundException, 
		NamingException, SQLException {
		
		EntidadeUsuario usuario = null;
		
		UsuarioControllerRep controllerRep = UsuarioControllerRep.getInstance();
		usuario = controllerRep.consultarCpfNovo(cpf);
		
		return usuario;
		
	}
	
	public void cadastrarUsuario(EntidadeUsuario usuario) throws Exception {
		
		UsuarioControllerRep controllerRep = UsuarioControllerRep.getInstance();
		controllerRep.cadastrarUsuario(usuario);
		
	}
	
	public void cadastrarUsuarioNovo(EntidadeUsuario usuario) throws Exception {
		
		UsuarioControllerRep controllerRep = UsuarioControllerRep.getInstance();
		controllerRep.cadastrarUsuarioNovo(usuario);
		
	}
	
	public List<String> getCidades()  throws ClassNotFoundException, 
		NamingException, SQLException {

		List<String> lista = new ArrayList<String>();
		UsuarioControllerRep rep = UsuarioControllerRep.getInstance();
		
		lista = rep.getCidades();
		
		return lista;
	}
	
	public boolean consUsuario(String cpf) throws ClassNotFoundException, 
		NamingException, SQLException {
		
		UsuarioControllerRep controllerRep = UsuarioControllerRep.getInstance();
		boolean flag = controllerRep.consUsuario(cpf);
		
		return flag;
	}
	
}
