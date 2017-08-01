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

import br.com.pe.urbana.dao.UserDao;
import br.com.pe.urbana.entidade.EntidadeUser;
import br.com.pe.urbana.util.Util;

public class UserControllerRep {
	
	private static final Logger LOG = Logger.getLogger(UserControllerRep.class);
	
	private static UserControllerRep instance;

	private UserControllerRep() {

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
	
    public static UserControllerRep getInstance() {

		if (instance == null) {
			instance = new UserControllerRep();
		}

		return instance;
	}
    
    public void cadastrarUser(EntidadeUser user) throws Exception {

    	UserDao dao = UserDao.getInstance();
    	dao.cadastrarUser(user); 	
	}
    
    public EntidadeUser validaLogin(String login) throws ClassNotFoundException, 
		NamingException, SQLException {
	
	    UserDao dao = UserDao.getInstance();
		ResultSet rs = dao.validaLogin(login);
				
		EntidadeUser user = null;
		
		if ((rs != null) && rs.next()) {
			
			user = new EntidadeUser();
			
			user.setId(rs.getInt("US_ID"));
			user.setNome(rs.getString("US_NOME"));
			user.setLogin(rs.getString("US_LOGIN"));
			user.setPassword(rs.getString("US_SENHA"));
			user.setRegUser(rs.getString("US_REGUSER"));
			user.setRegDate(rs.getDate("US_REGDATE"));
			
		}
		
		return user;
	}
    
    public boolean consultaUser(EntidadeUser user) throws ClassNotFoundException, 
		NamingException, SQLException {
	
	    UserDao dao = UserDao.getInstance();
		ResultSet rs = dao.consultaUser(user);
		
		boolean flag = false;
		
		if ((rs != null) && rs.next()) {
			flag = true;
		}
		
		return flag;
    }
    
}
