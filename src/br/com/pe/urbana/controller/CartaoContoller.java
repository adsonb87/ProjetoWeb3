package br.com.pe.urbana.controller;

import java.sql.SQLException;

import javax.naming.NamingException;

import br.com.pe.urbana.controller.rep.CartaoControllerRep;
import br.com.pe.urbana.entidade.EntidadeCartao;
import br.com.pe.urbana.entidade.EntidadeVinculacao;

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
	
	public void vincularCartaoUsuario(EntidadeVinculacao vinculacao) throws Exception {
		
		CartaoControllerRep controllerRep = CartaoControllerRep.getInstance();
		controllerRep.vincularCartaoUsuario(vinculacao);
		
	}
	
}
