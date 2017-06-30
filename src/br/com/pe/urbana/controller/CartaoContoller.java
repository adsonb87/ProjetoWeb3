package br.com.pe.urbana.controller;

import java.sql.SQLException;

import javax.naming.NamingException;

import br.com.pe.urbana.controller.rep.CartaoControllerRep;
import br.com.pe.urbana.entidade.EntidadeCartao;

public class CartaoContoller {

	private static CartaoContoller instance;

	public CartaoContoller() {

	}

	public static CartaoContoller getInstance() {

		if (instance == null) {
			instance = new CartaoContoller();
		}

		return instance;
	}
		
	public EntidadeCartao consultarCartao(int crdSnr) throws ClassNotFoundException, 
		NamingException, SQLException {
		
		EntidadeCartao card = null;
		
		CartaoControllerRep controllerRep = CartaoControllerRep.getInstance();
		card = controllerRep.consultarCartao(crdSnr);
		
		return card;
	}
	
}
