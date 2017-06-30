package br.com.pe.urbana.controller.rep;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import br.com.pe.urbana.dao.CartaoDAO;
import br.com.pe.urbana.entidade.EntidadeCartao;
import br.com.pe.urbana.entidade.Endereco;
import br.com.pe.urbana.entidade.EntidadeUsuario;
import br.com.pe.urbana.entidade.EntidadeVinculacao;
import br.com.pe.urbana.util.Util;

public class CartaoControllerRep {
	
	private static final Logger LOG = Logger.getLogger(CartaoControllerRep.class);
	
	private static CartaoControllerRep instance;

	private CartaoControllerRep() {

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
	
    public static CartaoControllerRep getInstance() {

		if (instance == null) {
			instance = new CartaoControllerRep();
		}

		return instance;
	}
    
    public EntidadeCartao consultarCartao(int crdSnr) throws ClassNotFoundException, 
		NamingException, SQLException {
	
	    CartaoDAO dao = CartaoDAO.getInstance();
		ResultSet rs = dao.consultarCartao(crdSnr);
		EntidadeCartao card = null;

		
		if ((rs != null) && rs.next()) {
			
			card = new EntidadeCartao();
			
			card.setUsrId(rs.getInt("USR_ID"));
			card.setViaCartao(rs.getString("VIA_CARTAO"));
			card.setMotivoBloq(rs.getString("MOT_BLOQ"));
			card.setCrdSnr(rs.getInt("CRD_SNR"));
			card.setCrdSnrAtual(rs.getInt("CRD_SNR_ATUAL"));
			card.setCpf(rs.getString("CPF"));
						
		}
		
		return card;
	}

}
