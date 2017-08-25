package br.com.pe.urbana.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import br.com.pe.urbana.controller.rep.BoletoControllerRep;
import br.com.pe.urbana.entidade.EntidadeCobranca;
import br.com.pe.urbana.entidade.EntidadeNossoNumero;

public class BoletoContoller {

	private static BoletoContoller instance;

	public BoletoContoller() {

	}

	public static BoletoContoller getInstance() {

		if (instance == null) {
			instance = new BoletoContoller();
		}

		return instance;
	}
	
	public void salvarCobranca(EntidadeCobranca cobranca) throws Exception {
		
		BoletoControllerRep controllerRep = BoletoControllerRep.getInstance();
		controllerRep.salvarCobranca(cobranca);
		controllerRep.atualizarNossoNumero(cobranca.getNossoNumero());
		
	}
		
	public EntidadeNossoNumero getNossoNumero() throws ClassNotFoundException, 
		NamingException, SQLException {
		
		EntidadeNossoNumero nossoNumero = null;
		
		BoletoControllerRep controllerRep = BoletoControllerRep.getInstance();
		nossoNumero = controllerRep.getNossoNumero();
		
		return nossoNumero;
	}
	
	public EntidadeCobranca consultarBoleto(Integer id) throws ClassNotFoundException, 
		NamingException, SQLException {
	
		EntidadeCobranca cobranca = null;
		
		BoletoControllerRep controllerRep = BoletoControllerRep.getInstance();
		cobranca = controllerRep.consultarBoleto(id);
	
		return cobranca;
	}
	
	public boolean consultarRegistro(String cpf) throws ClassNotFoundException, 
		NamingException, SQLException {
		
		BoletoControllerRep controllerRep = BoletoControllerRep.getInstance();
		boolean flag = controllerRep.consultarRegistro(cpf);
		
		return flag;
	}
	
	public List<EntidadeCobranca> listarCobrancas(String cpf)  throws ClassNotFoundException, 
		NamingException, SQLException {
	
		BoletoControllerRep controllerRep = BoletoControllerRep.getInstance();
		List<EntidadeCobranca> lista = new ArrayList<EntidadeCobranca>();

		lista = controllerRep.listarCobrancas(cpf);
		
		return lista;
	}
	
}
