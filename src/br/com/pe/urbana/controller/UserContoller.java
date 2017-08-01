package br.com.pe.urbana.controller;

import java.sql.SQLException;

import javax.naming.NamingException;

import br.com.pe.urbana.controller.rep.UserControllerRep;
import br.com.pe.urbana.entidade.EntidadeUser;

public class UserContoller {

	private static UserContoller instance;

	public UserContoller() {

	}

	public static UserContoller getInstance() {

		if (instance == null) {
			instance = new UserContoller();
		}

		return instance;
	}
	
	public void cadastrarUser(EntidadeUser user)  throws Exception {
		
		UserControllerRep controllerRep = UserControllerRep.getInstance();
		controllerRep.cadastrarUser(user);
		
	}
	
	public EntidadeUser validaLogin(String login) throws ClassNotFoundException, 
		NamingException, SQLException {
		
		UserControllerRep controllerRep = UserControllerRep.getInstance();
		EntidadeUser user = controllerRep.validaLogin(login);
		
		return user;
	}
	
	public boolean consultaUser(EntidadeUser user) throws ClassNotFoundException, 
		NamingException, SQLException {
		
		UserControllerRep controllerRep = UserControllerRep.getInstance();
		boolean flag = controllerRep.consultaUser(user);
		
		return flag;
	}
	
}
