package br.com.pe.urbana.controller.rep;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import br.com.pe.urbana.dao.VinculacaoDAO;
import br.com.pe.urbana.entidade.EntidadeVinculacao;
import br.com.pe.urbana.util.Util;

public class VinculacaoControllerRep {
	
	private static final Logger LOG = Logger.getLogger(VinculacaoControllerRep.class);
	
	private static VinculacaoControllerRep instance;

	private VinculacaoControllerRep() {

	}
    
	static {
		// Configura o Log4j com o arquivo do projeto
    	Properties props = new Properties();
    	try {
			props.load(new FileInputStream(Util.PATH));
		} catch (FileNotFoundException e) {
			LOG.error("Arquivo não encontrado", e);
		} catch (IOException e) {
			LOG.error("Arquivo não encontrado", e);
		}
    	PropertyConfigurator.configure(props);
	}
	
    public static VinculacaoControllerRep getInstance() {

		if (instance == null) {
			instance = new VinculacaoControllerRep();
		}

		return instance;
	}
    
    public void vincularCartaoUsuario(EntidadeVinculacao vinculacao) throws Exception {

		VinculacaoDAO dao = VinculacaoDAO.getInstance();
		dao.vincularCartaoUsuario(vinculacao);
	}

}
