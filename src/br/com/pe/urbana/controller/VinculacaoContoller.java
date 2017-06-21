package br.com.pe.urbana.controller;

import br.com.pe.urbana.controller.rep.VinculacaoControllerRep;
import br.com.pe.urbana.entidade.EntidadeVinculacao;

public class VinculacaoContoller {

	private static VinculacaoContoller instance;

	public VinculacaoContoller() {

	}

	public static VinculacaoContoller getInstance() {

		if (instance == null) {
			instance = new VinculacaoContoller();
		}

		return instance;
	}
	
	public void vincularCartaoUsuario(EntidadeVinculacao vinculacao) throws Exception {
		
		VinculacaoControllerRep controllerRep = VinculacaoControllerRep.getInstance();
		controllerRep.vincularCartaoUsuario(vinculacao);
		
	}
	
}
