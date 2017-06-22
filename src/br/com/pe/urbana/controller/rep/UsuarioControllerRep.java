package br.com.pe.urbana.controller.rep;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import br.com.pe.urbana.dao.UsuarioDAO;
import br.com.pe.urbana.entidade.EntidadeEndereco;
import br.com.pe.urbana.entidade.EntidadeUsuario;
import br.com.pe.urbana.util.Util;

public class UsuarioControllerRep {
	
	private static final Logger LOG = Logger.getLogger(UsuarioControllerRep.class);
	
	private static UsuarioControllerRep instance;

	private UsuarioControllerRep() {

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
	
    public static UsuarioControllerRep getInstance() {

		if (instance == null) {
			instance = new UsuarioControllerRep();
		}

		return instance;
	}
    
    public void cadastrarUsuario(EntidadeUsuario usuario) throws Exception {

		UsuarioDAO dao = UsuarioDAO.getInstance();
		dao.cadastrarUsuario(usuario);
	}
    
    public void atualizarUsuario(EntidadeUsuario usuario) throws Exception {
    	
    	UsuarioDAO dao = UsuarioDAO.getInstance();
    	dao.atualizarUsuario(usuario);
    }
    
    public List<String> getCidades()  throws ClassNotFoundException, 
		NamingException, SQLException {
	
		UsuarioDAO dao = UsuarioDAO.getInstance();
		List<String> lista = null;
		ResultSet rs = dao.getCidades();
	
		if (rs != null) {
			lista = new ArrayList<String>();
			while (rs.next()) {
				String cidade = rs.getString("CDD_NOME");
				lista.add(cidade);
			}
		
		}
		
		return lista;
	}
    
    public List<EntidadeUsuario> listarUsuarios() throws ClassNotFoundException, 
		NamingException, SQLException {

	    UsuarioDAO dao = UsuarioDAO.getInstance();
		ResultSet rs = dao.listarUsuarios();
		
		List<EntidadeUsuario> lista = null;
	
		if (rs != null) {
			lista = new ArrayList<EntidadeUsuario>();
			while (rs.next()) {
				EntidadeUsuario usuario = new EntidadeUsuario();
				
				usuario.setId(rs.getInt("USU_ID"));
				usuario.setNome(rs.getString("USU_NOME"));
				usuario.setCpf(rs.getString("USU_CPF"));
	
				lista.add(usuario);
			}
		}
			
		return lista;
    }
    
    public EntidadeUsuario consultarUsuario(String cpf) throws ClassNotFoundException, 
    	NamingException, SQLException {
	
	    UsuarioDAO dao = UsuarioDAO.getInstance();
		ResultSet rs = dao.consultarUsuario(cpf);
		
		EntidadeUsuario usuario = null;
		EntidadeEndereco endereco = null;
		
		if ((rs != null) && rs.next()) {
			usuario = new EntidadeUsuario();
			endereco = new EntidadeEndereco();

			usuario.setUsrId(rs.getInt("USR_ID"));
			usuario.setCpf(rs.getString("CPF"));
			usuario.setNome(rs.getString("NOME"));
			usuario.setTelefone(rs.getString("TELEFONE"));
			usuario.setEmail(rs.getString("EMAIL"));
			endereco.setLogradouro(rs.getString("LOGRADOURO"));
			endereco.setNumero(rs.getString("NUMERO"));
			endereco.setBairro(rs.getString("BAIRRO"));
			endereco.setCidade(rs.getString("CIDADE"));
			endereco.setUf(rs.getString("UF"));
			endereco.setCep(rs.getString("CEP"));
			endereco.setComplemento(rs.getString("COMPLEMENTO"));
			usuario.setEndereco(endereco);
			
		}			
	
		return usuario;
    }
    
    public boolean validarNovoUsuario(String cpf) throws ClassNotFoundException, 
    	NamingException, SQLException {
		
    	UsuarioDAO dao = UsuarioDAO.getInstance();
		ResultSet rs = dao.validarNovoUsuario(cpf);
		
		boolean flag = false;

		if ((rs != null) && rs.next()) {
			flag = true;
		}
		
		return flag;
	}
    
    public EntidadeUsuario consultarNovoUsuario(String cpf) throws ClassNotFoundException, 
		NamingException, SQLException {
	
	    UsuarioDAO dao = UsuarioDAO.getInstance();
		ResultSet rs = dao.consultarNovoUsuario(cpf);
		
		EntidadeUsuario usuario = null;
		EntidadeEndereco endereco = null;
		
		if ((rs != null) && rs.next()) {
			usuario = new EntidadeUsuario();
			endereco = new EntidadeEndereco();
	
			usuario.setId(rs.getInt("USU_ID"));
			usuario.setUsrId(rs.getInt("USR_ID"));
			usuario.setNome(rs.getString("USU_NOME"));
			usuario.setCpf(rs.getString("USU_CPF"));
			usuario.setTelefone(rs.getString("USU_FONE"));
			usuario.setEmail(rs.getString("USU_EMAIL"));
			endereco.setLogradouro(rs.getString("USU_LOGRADOURO"));
			endereco.setNumero(rs.getString("USU_NUMERO"));
			endereco.setBairro(rs.getString("USU_BAIRRO"));
			endereco.setCidade(rs.getString("USU_CIDADE"));
			endereco.setUf(rs.getString("USU_UF"));
			endereco.setCep(rs.getString("USU_CEP"));
			endereco.setComplemento(rs.getString("USU_COMPLEMENTO"));
			usuario.setEndereco(endereco);
			
		}			
	
		return usuario;
	}
    
}
