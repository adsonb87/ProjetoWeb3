package br.com.pe.urbana.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.naming.NamingException;

import br.com.pe.urbana.entidade.EntidadeUsuario;
import br.com.pe.urbana.factory.ConnectionFactoryDB;
import br.com.pe.urbana.factory.util.ParamDAO;

public class UsuarioDAO extends ConnectionFactoryDB {
	
	private static UsuarioDAO instance;

	private UsuarioDAO() {

	}

	public static UsuarioDAO getInstance() {

		if (instance == null) {
			instance = new UsuarioDAO();
		}

		return instance;
	}
	
	public void cadastrarUsuario(EntidadeUsuario usuario) throws Exception{
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("	INSERT INTO COM_USUARIO (USR_ID, USU_NOME, USU_CPF, USU_CEP, USU_LOGRADOURO,");
		sb.append("		USU_NUMERO, USU_BAIRRO, USU_CIDADE, USU_UF, USU_COMPLEMENTO, USU_FONE, USU_EMAIL)");
		sb.append("	VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		
		ParamDAO[] params = new ParamDAO[12];
		
		params[0] = new ParamDAO(usuario.getUsrId(), Types.INTEGER);
		params[1] = new ParamDAO(usuario.getNome(), Types.VARCHAR);
		params[2] = new ParamDAO(usuario.getCpf(), Types.VARCHAR);
		params[3] = new ParamDAO(Integer.parseInt(usuario.getEndereco().getCep()), Types.INTEGER);
		params[4] = new ParamDAO(usuario.getEndereco().getLogradouro(), Types.VARCHAR);
		params[5] = new ParamDAO(usuario.getEndereco().getNumero(), Types.VARCHAR);
		params[6] = new ParamDAO(usuario.getEndereco().getBairro(), Types.VARCHAR);
		params[7] = new ParamDAO(usuario.getEndereco().getCidade(), Types.VARCHAR);
		params[8] = new ParamDAO(usuario.getEndereco().getUf(), Types.VARCHAR);
		params[9] = new ParamDAO(usuario.getEndereco().getComplemento(), Types.VARCHAR);
		params[10] = new ParamDAO(usuario.getTelefone(), Types.VARCHAR);
		params[11] = new ParamDAO(usuario.getEmail(), Types.VARCHAR);
		
		super.executeUpdate(sb.toString(), params);
		
	}
	
	public void atualizarUsuario(EntidadeUsuario usuario) throws Exception {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("	UPDATE COM_USUARIO SET USU_NOME = ?, USU_CEP = ?, USU_LOGRADOURO = ?, USU_NUMERO = ?,");
		sb.append("		USU_BAIRRO = ?, USU_CIDADE = ?, USU_UF = ?, USU_COMPLEMENTO = ?, USU_FONE = ?, USU_EMAIL = ?");
		sb.append(" WHERE USU_CPF = ?");
		
		ParamDAO[] params = new ParamDAO[11];
		
		params[0] = new ParamDAO(usuario.getNome(), Types.VARCHAR);
		params[1] = new ParamDAO(Integer.parseInt(usuario.getEndereco().getCep()), Types.INTEGER);
		params[2] = new ParamDAO(usuario.getEndereco().getLogradouro(), Types.VARCHAR);
		params[3] = new ParamDAO(usuario.getEndereco().getNumero(), Types.VARCHAR);
		params[4] = new ParamDAO(usuario.getEndereco().getBairro(), Types.VARCHAR);
		params[5] = new ParamDAO(usuario.getEndereco().getCidade(), Types.VARCHAR);
		params[6] = new ParamDAO(usuario.getEndereco().getUf(), Types.VARCHAR);
		params[7] = new ParamDAO(usuario.getEndereco().getComplemento(), Types.VARCHAR);
		params[8] = new ParamDAO(usuario.getTelefone(), Types.VARCHAR);
		params[9] = new ParamDAO(usuario.getEmail(), Types.VARCHAR);
		params[10] = new ParamDAO(usuario.getCpf(), Types.VARCHAR);
		
		super.executeUpdate(sb.toString(), params);
	
	}
	
	public ResultSet getCidades() throws ClassNotFoundException, NamingException, SQLException {
	
		StringBuilder sb = new StringBuilder();
		
		sb.append(" SELECT CDD_NOME ");	
		sb.append(" FROM CLA_CIDADE");
		
		ResultSet rs = super.executeQuery(sb.toString());
		
		return rs;
	
	}
	
	public ResultSet listarUsuarios() throws ClassNotFoundException, NamingException, SQLException{
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(" SELECT USU_ID, USU_NOME, USU_CPF"); 
		sb.append(" FROM COM_USUARIO");
		sb.append(" ORDER BY USU_ID ASC");
		
		ResultSet rs = super.executeQuery(sb.toString());
		
		return rs;
	}
	
	public ResultSet consultarUsuario(String cpf) throws ClassNotFoundException, NamingException, SQLException{
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(" SELECT "); 
		sb.append(" 	US.USR_ID,"); 
		sb.append(" 	UDC.USRDOC_NUMBER CPF,");
		sb.append(" 	US.USR_NAME NOME,"); 
		sb.append(" 	UTP.USRTEL_NUMBER TELEFONE,"); 
		sb.append("		UM.USREM_ACCOUNT EMAIL,");
		sb.append(" 	UAS.USRADD_ADDRESS LOGRADOURO,"); 
		sb.append(" 	UAS.USRADD_ADDNBR NUMERO,"); 
		sb.append(" 	UAS.USRADD_DISTRICT BAIRRO,"); 
		sb.append(" 	UAS.USRADD_CITY CIDADE,"); 
		sb.append(" 	UAS.ST_CODE UF,"); 
		sb.append("		UAS.USRADD_ZIP CEP,");
		sb.append("		UAS.USRADD_ADDCOMP COMPLEMENTO");
		sb.append(" FROM MERCURY.USERS US"); 
		sb.append(" INNER JOIN MERCURY.USERDOCUMENTS UDC ON UDC.USR_ID = US.USR_ID AND UDC.USRDOC_STATUS = 'A'"); 
		sb.append(" INNER JOIN MERCURY.USERADDRESSES UAS ON UAS.USR_ID = US.USR_ID AND UAS.USRADD_STATUS = 'A'"); 
		sb.append(" LEFT JOIN MERCURY.USERTELEPHONES UTP ON UTP.USR_ID = UAS.USR_ID AND UTP.USRTEL_STATUS = 'A'");
		sb.append("	LEFT JOIN MERCURY.USEREMAILS UM ON UM.USR_ID = US.USR_ID AND UM.USREM_STATUS = 'A'");
		sb.append(" WHERE USRDOC_NUMBER = ?");
		
		ParamDAO[] params = new ParamDAO[1];
		
		params[0] = new ParamDAO(cpf, Types.VARCHAR);
		
		ResultSet rs = super.executeQuery(sb.toString(), params);
		
		return rs;
	}
	
	public ResultSet consultarCPF(String cpf) throws ClassNotFoundException, NamingException, SQLException{
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("	SELECT *");
		sb.append("	FROM COM_USUARIO");
		sb.append("	WHERE USU_CPF = ?");

		ParamDAO[] params = new ParamDAO[1];
		
		params[0] = new ParamDAO(cpf, Types.VARCHAR);
		
		ResultSet rs = super.executeQuery(sb.toString(), params);
		
		return rs;
	}
	
}
