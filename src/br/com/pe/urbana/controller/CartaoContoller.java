package br.com.pe.urbana.controller;

import java.sql.SQLException;

import javax.naming.NamingException;

import br.com.pe.urbana.controller.rep.CartaoControllerRep;
import br.com.pe.urbana.entidade.EntidadeCartao;
import br.com.pe.urbana.entidade.EntidadeUsuario;

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
		
	public EntidadeCartao consultarCartao(String crdSnr) throws ClassNotFoundException, 
		NamingException, SQLException {
		
		EntidadeCartao card = null;
		
		CartaoControllerRep controllerRep = CartaoControllerRep.getInstance();
		card = controllerRep.consultarCartao(crdSnr);
		
		return card;
	}
	
	public EntidadeCartao consultarCartaoVinculado(int usrIdCartao) throws ClassNotFoundException, 
		NamingException, SQLException {

		EntidadeCartao card = null;

		CartaoControllerRep controllerRep = CartaoControllerRep.getInstance();
		card = controllerRep.consultarCartaoVinculado(usrIdCartao);
		
		return card;
	}
	
	public void vincularCardUser(EntidadeUsuario usuario) throws Exception {
		
		CartaoControllerRep controllerRep = CartaoControllerRep.getInstance();
		controllerRep.vincularCardUser(usuario);
		
	}
	
	public void atualizarCardUser(EntidadeUsuario usuario) throws Exception {
		
		CartaoControllerRep controllerRep = CartaoControllerRep.getInstance();
		controllerRep.atualizarCardUser(usuario);
		
	}
	
}
