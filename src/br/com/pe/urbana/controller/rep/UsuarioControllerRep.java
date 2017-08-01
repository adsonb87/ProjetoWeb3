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

import br.com.pe.urbana.dao.UsuarioDao;
import br.com.pe.urbana.entidade.EnderecoUsuario;
import br.com.pe.urbana.entidade.EntidadeCartao;
import br.com.pe.urbana.entidade.EntidadeUsuario;
import br.com.pe.urbana.util.Util;

public class UsuarioControllerRep {
	
	private static final Logger LOG = Logger.getLogger(UsuarioControllerRep.class);
	
	private static UsuarioControllerRep instance;

	private UsuarioControllerRep() {

	}
    
	static {
		// CONFIGURA O LOG4J COM O ARQUIVO DO PROJET
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
    
    public boolean consultarUsuario(String cpf) throws ClassNotFoundException, 
		NamingException, SQLException {
		
		UsuarioDao dao = UsuarioDao.getInstance();
		ResultSet rs = dao.consultarUsuario(cpf);
		
		boolean flag = false;
	
		if ((rs != null) && rs.next()) {
			flag = true;
		}
		
		return flag;
    }
    
    public boolean consultarUsuarioNovo(String cpf) throws ClassNotFoundException, 
		NamingException, SQLException {
		
		UsuarioDao dao = UsuarioDao.getInstance();
		ResultSet rs = dao.consultarUsuarioNovo(cpf);
		
		boolean flag = false;
	
		if ((rs != null) && rs.next()) {
			flag = true;
		}
		
		return flag;
    }
    
    public EntidadeUsuario consultarCpf(String cpf) throws ClassNotFoundException, 
		NamingException, SQLException {
	
	    UsuarioDao dao = UsuarioDao.getInstance();
		ResultSet rs = dao.consultarCpf(cpf);
		
		EntidadeUsuario usuario = null;
		EnderecoUsuario endereco = null;
		
		if ((rs != null) && rs.next()) {
			usuario = new EntidadeUsuario();
			endereco = new EnderecoUsuario();
	
			usuario.setUsrIdOrigem(rs.getInt("USR_ID"));
			usuario.setCpf(rs.getString("CPF"));
			usuario.setNome(rs.getString("NOME"));
			usuario.setDataNascimento(rs.getString("DATA_NASC"));
			usuario.setNomeMae(rs.getString("NOME_MAE"));
			usuario.setTelefone(rs.getString("TELEFONE"));
			usuario.setEmail(rs.getString("EMAIL"));
			endereco.setCep(rs.getString("CEP"));
			endereco.setLogradouro(rs.getString("LOGRADOURO"));
			endereco.setNumero(rs.getString("NUMERO"));
			endereco.setBairro(rs.getString("BAIRRO"));
			endereco.setCidade(rs.getString("CIDADE"));
			endereco.setUf(rs.getString("UF"));
			endereco.setComplemento(rs.getString("COMPLEMENTO"));
			usuario.setEndereco(endereco);
			
		}			
	
		return usuario;
	}
    
    public EntidadeUsuario consultarCpfNovo(String cpf) throws ClassNotFoundException, 
		NamingException, SQLException {
	
	    UsuarioDao dao = UsuarioDao.getInstance();
		ResultSet rs = dao.consultarCpfNovo(cpf);
		
		EntidadeUsuario usuario = null;
		EnderecoUsuario endereco = null;
		EntidadeCartao cartao = null;
		
		if ((rs != null) && rs.next()) {
			usuario = new EntidadeUsuario();
			endereco = new EnderecoUsuario();
			cartao = new EntidadeCartao();
			
			usuario.setId(rs.getInt("USU_ID"));
			usuario.setUsrIdOrigem(rs.getInt("USR_ID_ORIG"));
			usuario.setCpf(rs.getString("USU_CPF"));
			usuario.setNome(rs.getString("USU_NOME"));
			usuario.setDataNascimento(rs.getString("USU_DT_NASC"));
			usuario.setNomeMae(rs.getString("USU_NOME_MAE"));
			usuario.setTelefone(rs.getString("USU_TELEFONE"));
			usuario.setEmail(rs.getString("USU_EMAIL"));
			
			endereco.setCep(String.valueOf(rs.getInt("USU_CEP")));
			endereco.setLogradouro(rs.getString("USU_LOGRADOURO"));
			endereco.setNumero(String.valueOf(rs.getInt("USU_NUMERO")));
			endereco.setBairro(rs.getString("USU_BAIRRO"));
			endereco.setCidade(rs.getString("USU_CIDADE"));
			endereco.setUf(rs.getString("USU_UF"));
			endereco.setComplemento(rs.getString("USU_COMPLEMENTO"));
			usuario.setEndereco(endereco);
			
			cartao.setUsrIdCartao(rs.getInt("USR_ID_CART"));
			usuario.setCartao(cartao);
			
		}			
	
		return usuario;
	}
    
    public void cadastrarUsuario(EntidadeUsuario usuario) throws Exception {

		UsuarioDao dao = UsuarioDao.getInstance();
		dao.cadastrarUsuario(usuario);
	}
    
    public void cadastrarUsuarioNovo(EntidadeUsuario usuario) throws Exception {

		UsuarioDao dao = UsuarioDao.getInstance();
		dao.cadastrarUsuarioNovo(usuario);
	}
        
    public List<String> getCidades()  throws ClassNotFoundException, 
		NamingException, SQLException {
	
		UsuarioDao dao = UsuarioDao.getInstance();
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
   
    public boolean consUsuario(String cpf) throws ClassNotFoundException, 
		NamingException, SQLException {
		
		UsuarioDao dao = UsuarioDao.getInstance();
		ResultSet rs = dao.consUsuario(cpf);
		
		boolean flag = false;
	
		if ((rs != null) && rs.next()) {
			flag = true;
		}
		
		return flag;
    }
    
}
