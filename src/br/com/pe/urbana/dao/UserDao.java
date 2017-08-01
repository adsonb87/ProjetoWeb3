package br.com.pe.urbana.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.naming.NamingException;

import br.com.pe.urbana.entidade.EntidadeUser;
import br.com.pe.urbana.factory.ConnectionFactoryDB;
import br.com.pe.urbana.factory.util.ParamDAO;

public class UserDao extends ConnectionFactoryDB {
	
	private static UserDao instance;

	private UserDao() {

	}

	public static UserDao getInstance() {

		if (instance == null) {
			instance = new UserDao();
		}

		return instance;
	}
	
	public void cadastrarUser(EntidadeUser user) throws Exception {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("	INSERT INTO COM_USER (US_NOME, US_LOGIN, US_SENHA,");
		sb.append("		US_REGUSER, US_REGDATE)");
		sb.append("	VALUES (?, ?, ?, ?, SYSDATE)");
		
		ParamDAO[] params = new ParamDAO[4];
		
		params[0] = new ParamDAO(user.getNome(), Types.VARCHAR);
		params[1] = new ParamDAO(user.getLogin(), Types.VARCHAR);
		params[2] = new ParamDAO(user.getPassword(), Types.VARCHAR);
		params[3] = new ParamDAO(user.getRegUser(), Types.VARCHAR);
		
		super.executeUpdate(sb.toString(), params);
		
	}
	
	public ResultSet validaLogin(String login) throws ClassNotFoundException, NamingException, SQLException{
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("	SELECT ");
		sb.append("		US_ID,");
		sb.append("		US_NOME,");
		sb.append("		US_LOGIN,");
		sb.append("		US_SENHA,");
		sb.append("		US_REGUSER,");
		sb.append("		US_REGDATE");
		sb.append("	FROM COM_USER");
		sb.append("	WHERE US_LOGIN = ?");

		ParamDAO[] params = new ParamDAO[1];
		
		params[0] = new ParamDAO(login, Types.VARCHAR);
		
		ResultSet rs = super.executeQuery(sb.toString(), params);
		
		return rs;
	}
	
	public ResultSet consultaUser(EntidadeUser user) throws ClassNotFoundException, NamingException, SQLException{
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("	SELECT *");
		sb.append("	FROM COM_USER");
		sb.append("	WHERE US_LOGIN = ?");

		ParamDAO[] params = new ParamDAO[1];
		
		params[0] = new ParamDAO(user.getLogin(), Types.VARCHAR);
		
		ResultSet rs = super.executeQuery(sb.toString(), params);
		
		return rs;
	}
	
}
