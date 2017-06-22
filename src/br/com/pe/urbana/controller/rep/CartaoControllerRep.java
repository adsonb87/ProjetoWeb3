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
import br.com.pe.urbana.entidade.EntidadeEndereco;
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
    
    public EntidadeCartao consultarCartao(EntidadeCartao cartao) throws ClassNotFoundException, 
		NamingException, SQLException {
	
	    CartaoDAO dao = CartaoDAO.getInstance();
		ResultSet rs = dao.consultarCartao(cartao);
		EntidadeCartao card = null;
		EntidadeUsuario usuario = null;
		EntidadeEndereco endereco = null;
		
		if ((rs != null) && rs.next()) {
			
			card = new EntidadeCartao();
			usuario = new EntidadeUsuario();
			endereco = new EntidadeEndereco();
			
			card.setProjeto(rs.getInt("PROJETO"));
			card.setDesign(rs.getInt("DESIGN"));
			card.setNumeroExterno(rs.getInt("NUMEXTERNO"));
			usuario.setUsrId(rs.getInt("USR_ID"));
			usuario.setCpf(rs.getString("CPF"));
			usuario.setNome(rs.getString("NOME"));
			usuario.setTelefone(rs.getString("TELEFONE"));
			endereco.setLogradouro(rs.getString("LOGRADOURO"));
			endereco.setNumero(rs.getString("NUMERO"));
			endereco.setBairro(rs.getString("BAIRRO"));
			endereco.setCidade(rs.getString("CIDADE"));
			endereco.setUf(rs.getString("UF"));
			endereco.setCep(rs.getString("CEP"));
			endereco.setComplemento(rs.getString("COMPLEMENTO"));
			usuario.setEndereco(endereco);
			card.setUsuario(usuario);
			
		}
		
		return card;
	}
    
    public boolean validarCartao(EntidadeCartao cartao) throws ClassNotFoundException, 
    	NamingException, SQLException {
	
	    CartaoDAO dao = CartaoDAO.getInstance();
		ResultSet rs = dao.validarCartao(cartao);
				
		boolean flag = false;

		if ((rs != null) && rs.next()) {
			flag = true;
		}
		
		return flag;
    }
    
    public void vincularCartaoUsuario(EntidadeVinculacao vinculacao) throws Exception {

	    CartaoDAO dao = CartaoDAO.getInstance();
		dao.vincularCartaoUsuario(vinculacao);
	}

}
