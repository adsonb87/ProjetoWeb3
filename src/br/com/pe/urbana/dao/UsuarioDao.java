package br.com.pe.urbana.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.naming.NamingException;

import br.com.pe.urbana.entidade.EntidadeUsuario;
import br.com.pe.urbana.factory.ConnectionFactoryDB;
import br.com.pe.urbana.factory.util.ParamDAO;

public class UsuarioDao extends ConnectionFactoryDB {
	
	private static UsuarioDao instance;

	private UsuarioDao() {

	}

	public static UsuarioDao getInstance() {

		if (instance == null) {
			instance = new UsuarioDao();
		}

		return instance;
	}
	
	public ResultSet consultarUsuario(String cpf) throws ClassNotFoundException, NamingException, SQLException{
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("	SELECT US.*");
		sb.append("	FROM USERS US");
		sb.append("	INNER JOIN USERDOCUMENTS UD ON UD.USR_ID = US.USR_ID AND DT_ID IN (89)");
		sb.append("	WHERE UD.USRDOC_NUMBER = ?");
		
		ParamDAO[] params = new ParamDAO[1];
		
		params[0] = new ParamDAO(cpf, Types.VARCHAR);
		
		ResultSet rs = super.executeQuery(sb.toString(), params);
		
		return rs;
	}
	
	public ResultSet consultarUsuarioNovo(String cpf) throws ClassNotFoundException, NamingException, SQLException{
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("	SELECT *");
		sb.append("	FROM COM_USUARIO");
		sb.append("	WHERE USU_CPF = ?");

		ParamDAO[] params = new ParamDAO[1];
		
		params[0] = new ParamDAO(cpf, Types.VARCHAR);
		
		ResultSet rs = super.executeQuery(sb.toString(), params);
		
		return rs;
	}
	
	public ResultSet consultarCpf(String cpf) throws ClassNotFoundException, NamingException, SQLException{
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(" SELECT ");
		sb.append("	 	UDC.USR_ID,");
		sb.append("		UDC.USRDOC_NUMBER CPF,");
		sb.append("		US.USR_NAME NOME,");
		sb.append("		TO_CHAR (US.USR_BIRTHDATE,'DD/MM/YYYY') DATA_NASC,");
		sb.append("		US.USR_NAMEMOTHER NOME_MAE,");
		sb.append("		UT.USRTEL_NUMBER TELEFONE,");
		sb.append("		UM.USREM_ACCOUNT EMAIL,");
		sb.append("		UA.USRADD_ZIP CEP,");
		sb.append("		UA.USRADD_ADDRESS LOGRADOURO,");
		sb.append("		UA.USRADD_ADDNBR NUMERO,");
		sb.append("		UA.USRADD_DISTRICT BAIRRO,");
		sb.append("		UA.USRADD_CITY CIDADE,");
		sb.append("		UA.ST_CODE UF,");
		sb.append("	UA.USRADD_ADDCOMP COMPLEMENTO");
		sb.append(" FROM USERS US");
		sb.append(" INNER JOIN MERCURY.USERDOCUMENTS UDC ON UDC.USR_ID = US.USR_ID AND UDC.USRDOC_STATUS = 'A'");
		sb.append(" INNER JOIN MERCURY.USERADDRESSES UA ON UA.USR_ID = US.USR_ID AND UA.USRADD_STATUS = 'A'");
		sb.append(" LEFT JOIN MERCURY.USERTELEPHONES UT ON UT.USR_ID = UA.USR_ID AND UT.USRTEL_STATUS = 'A'");
		sb.append(" LEFT JOIN MERCURY.USEREMAILS UM ON UM.USR_ID = US.USR_ID AND UM.USREM_STATUS = 'A'");
		sb.append(" WHERE USRDOC_NUMBER = ?");
		
		ParamDAO[] params = new ParamDAO[1];
		
		params[0] = new ParamDAO(cpf, Types.VARCHAR);
		
		ResultSet rs = super.executeQuery(sb.toString(), params);
		
		return rs;
	}
	
	public ResultSet consultarCpfNovo(String cpf) throws ClassNotFoundException, NamingException, SQLException{
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("	SELECT ");
		sb.append("		USU_ID,");
		sb.append("		USR_ID_CART,");
		sb.append("		USR_ID_ORIG,");
		sb.append("		USU_CPF,");
		sb.append("		USU_NOME,");
		sb.append("		TO_CHAR (USU_DT_NASC,'DD/MM/YYYY') USU_DT_NASC,");
		sb.append("		USU_NOME_MAE,");
		sb.append("		USU_TELEFONE,");
		sb.append("		USU_EMAIL,");
		sb.append("		USU_CEP,");
		sb.append("		USU_LOGRADOURO,");
		sb.append("		USU_NUMERO,");
		sb.append("		USU_BAIRRO,");
		sb.append("		USU_CIDADE,");
		sb.append("		USU_UF,");
		sb.append("		USU_COMPLEMENTO");
		sb.append("	FROM COM_USUARIO");
		sb.append("	WHERE USU_CPF = ?");

		ParamDAO[] params = new ParamDAO[1];
		
		params[0] = new ParamDAO(cpf, Types.VARCHAR);
		
		ResultSet rs = super.executeQuery(sb.toString(), params);
		
		return rs;
	}
	
	public void cadastrarUsuario(EntidadeUsuario usuario) throws Exception {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("	INSERT INTO COM_USUARIO (USU_CPF, USU_NOME, USU_DT_NASC,");
		sb.append("		USU_NOME_MAE, USU_TELEFONE, USU_EMAIL, USU_REGUSER, USU_REGDATE)");
		sb.append("	VALUES (?, ?, ?, ?, ?, ?, ?, SYSDATE)");
		
		ParamDAO[] params = new ParamDAO[7];
		
		params[0] = new ParamDAO(usuario.getCpf(), Types.VARCHAR);
		params[1] = new ParamDAO(usuario.getNome(), Types.VARCHAR);
		params[2] = new ParamDAO(usuario.getDataNascimento(), Types.VARCHAR);
		params[3] = new ParamDAO(usuario.getNomeMae(), Types.VARCHAR);
		params[4] = new ParamDAO(usuario.getTelefone(), Types.VARCHAR);
		params[5] = new ParamDAO(usuario.getEmail(), Types.VARCHAR);
		params[6] = new ParamDAO(usuario.getRegUser(), Types.VARCHAR);
		
		super.executeUpdate(sb.toString(), params);
		
	}
	
	public void cadastrarUsuarioNovo(EntidadeUsuario usuario) throws Exception {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("	INSERT INTO COM_USUARIO (USR_ID_ORIG, USU_CPF, USU_NOME, USU_DT_NASC, USU_NOME_MAE, USU_TELEFONE, USU_EMAIL,");
		sb.append("		USU_CEP, USU_LOGRADOURO, USU_NUMERO, USU_BAIRRO, USU_CIDADE, USU_UF, USU_COMPLEMENTO, USU_REGUSER, USU_REGDATE)");
		sb.append("	VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)");
		
		ParamDAO[] params = new ParamDAO[15];
		
		params[0] = new ParamDAO(usuario.getUsrIdOrigem() != 0 ? usuario.getUsrIdOrigem() : null, Types.INTEGER);
		params[1] = new ParamDAO(usuario.getCpf(), Types.VARCHAR);
		params[2] = new ParamDAO(usuario.getNome(), Types.VARCHAR);
		params[3] = new ParamDAO(usuario.getDataNascimento(), Types.VARCHAR);
		params[4] = new ParamDAO(usuario.getNomeMae(), Types.VARCHAR);
		params[5] = new ParamDAO(usuario.getTelefone(), Types.VARCHAR);
		params[6] = new ParamDAO(usuario.getEmail(), Types.VARCHAR);
		params[7] = new ParamDAO(usuario.getEndereco().getCep(), Types.VARCHAR);
		params[8] = new ParamDAO(usuario.getEndereco().getLogradouro(), Types.VARCHAR);
		params[9] = new ParamDAO(usuario.getEndereco().getNumero(), Types.VARCHAR);
		params[10] = new ParamDAO(usuario.getEndereco().getBairro(), Types.VARCHAR);
		params[11] = new ParamDAO(usuario.getEndereco().getCidade(), Types.VARCHAR);
		params[12] = new ParamDAO(usuario.getEndereco().getUf(), Types.VARCHAR);
		params[13] = new ParamDAO(usuario.getEndereco().getComplemento(), Types.VARCHAR);
		params[14] = new ParamDAO(usuario.getRegUser(), Types.VARCHAR);
				
		super.executeUpdate(sb.toString(), params);
		
	}
	
	public ResultSet getCidades() throws ClassNotFoundException, NamingException, SQLException {
	
		StringBuilder sb = new StringBuilder();
		
		sb.append(" SELECT CDD_NOME ");	
		sb.append(" FROM CLA_CIDADE");
		
		ResultSet rs = super.executeQuery(sb.toString());
		
		return rs;
	
	}
	
	public ResultSet consUsuario(String cpf) throws ClassNotFoundException, NamingException, SQLException{
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("	SELECT US.*");
		sb.append("	FROM USERS US");
		sb.append("	INNER JOIN USERDOCUMENTS UD ON UD.USR_ID = US.USR_ID");
		sb.append("	WHERE UD.USRDOC_NUMBER = ?");
		
		ParamDAO[] params = new ParamDAO[1];
		
		params[0] = new ParamDAO(cpf, Types.VARCHAR);
		
		ResultSet rs = super.executeQuery(sb.toString(), params);
		
		return rs;
	}
	
}
