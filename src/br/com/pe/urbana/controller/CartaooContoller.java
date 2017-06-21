package br.com.pe.urbana.controller;

import java.sql.SQLException;

import javax.naming.NamingException;

import br.com.pe.urbana.controller.rep.CartaoControllerRep;
import br.com.pe.urbana.entidade.EntidadeCartao;

public class CartaooContoller {

	private static CartaooContoller instance;

	public CartaooContoller() {

	}

	public static CartaooContoller getInstance() {

		if (instance == null) {
			instance = new CartaooContoller();
		}

		return instance;
	}
		
	public EntidadeCartao consultarCartao(EntidadeCartao cartao) throws ClassNotFoundException, 
		NamingException, SQLException {
		
		EntidadeCartao card = null;
		
		CartaoControllerRep controllerRep = CartaoControllerRep.getInstance();
		card = controllerRep.consultarCartao(cartao);
		
		return card;
	}
	
	public boolean validarCartao(EntidadeCartao cartao) throws ClassNotFoundException, 
		NamingException, SQLException {
				
		CartaoControllerRep controllerRep = CartaoControllerRep.getInstance();
		boolean flag = controllerRep.validarCartao(cartao);
		
		return flag;
		
	}
	
}
