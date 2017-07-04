package br.com.pe.urbana.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.naming.NamingException;

import br.com.pe.urbana.entidade.EntidadeUsuario;
import br.com.pe.urbana.factory.ConnectionFactoryDB;
import br.com.pe.urbana.factory.util.ParamDAO;

public class CartaoDAO extends ConnectionFactoryDB {
	
	private static CartaoDAO instance;

	private CartaoDAO() {

	}

	public static CartaoDAO getInstance() {

		if (instance == null) {
			instance = new CartaoDAO();
		}

		return instance;
	}
	
	public ResultSet consultarCartao(String crdSnr) throws ClassNotFoundException,
		NamingException, SQLException{
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(" SELECT ");
		sb.append(" 	CXU.USR_ID,");
		sb.append(" 	CASE WHEN CXU.CRD_SNR = CM.CRD_SNR THEN '1' ELSE '2' END VIA_CARTAO,");
		sb.append(" 	HR.HLR_ID MOT_BLOQ,");
		sb.append("		CXU.ISS_ID,");
		sb.append("		CXU.CD_ID,");
		sb.append(" 	CXU.CRD_SNR,");
		sb.append(" 	CM.CRD_SNR CRD_SNR_ATUAL,");
		sb.append(" 	UD.USRDOC_NUMBER CPF");
		sb.append(" FROM CARDSXUSERS CXU");
		sb.append(" LEFT JOIN (SELECT CXUMAX.USR_ID, MAX(CXUMAX.CRD_SNR) CRD_SNR FROM CARDSXUSERS CXUMAX WHERE CXUMAX.ISS_ID = 90 AND CXUMAX.CD_ID = 6 GROUP BY CXUMAX.USR_ID) CM ON CXU.USR_ID = CM.USR_ID");
		sb.append(" LEFT JOIN HOTLISTSINGLE HS ON HS.ISS_ID = CXU.ISS_ID AND HS.CD_ID = CXU.CD_ID AND HS.CRD_SNR = CXU.CRD_SNR");
		sb.append(" LEFT JOIN HOTLISTREASON HR ON HS.HLR_ID = HR.HLR_ID");
		sb.append(" LEFT JOIN USERDOCUMENTS UD ON UD.USR_ID = CXU.USR_ID AND DT_ID IN (6, 89)");
		sb.append(" WHERE CXU.ISS_ID = 90");
		sb.append(" AND CXU.CD_ID = 6");
		sb.append(" AND CXU.CRD_SNR = ?");
		
		ParamDAO[] params = new ParamDAO[1];

		params[0] = new ParamDAO(Integer.parseInt(crdSnr), Types.INTEGER);
		
		ResultSet rs = super.executeQuery(sb.toString(), params);
		
		return rs;
	}
	
	public ResultSet consultarCartaoVinculado(int usrIdCartao) throws ClassNotFoundException,
		NamingException, SQLException{
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(" SELECT USU_CPF");
		sb.append(" FROM COM_USUARIO");
		sb.append(" WHERE USR_ID_CART = ?");
		
		ParamDAO[] params = new ParamDAO[1];
	
		params[0] = new ParamDAO(usrIdCartao, Types.INTEGER);
		
		ResultSet rs = super.executeQuery(sb.toString(), params);
		
		return rs;
	}
	
	public void vincularCardUser(EntidadeUsuario usuario) throws Exception {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("	INSERT INTO COM_USUARIO (USR_ID_CART, USR_ID_ORIG, USU_CPF, USU_NOME,");
		sb.append("		USU_DT_NASC, USU_NOME_MAE, USU_TELEFONE, USU_EMAIL, USU_REGDATE)");
		sb.append("	VALUES (?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)");
		
		ParamDAO[] params = new ParamDAO[8];
		
		params[0] = new ParamDAO(usuario.getCartao().getUsrIdCartao(), Types.INTEGER);
		params[1] = new ParamDAO(usuario.getUsrIdOrigem() != 0 ? usuario.getUsrIdOrigem() : null, Types.INTEGER);
		params[2] = new ParamDAO(usuario.getCpf(), Types.VARCHAR);
		params[3] = new ParamDAO(usuario.getNome(), Types.VARCHAR);
		params[4] = new ParamDAO(usuario.getDataNascimento(), Types.VARCHAR);
		params[5] = new ParamDAO(usuario.getNomeMae(), Types.VARCHAR);
		params[6] = new ParamDAO(usuario.getTelefone(), Types.VARCHAR);
		params[7] = new ParamDAO(usuario.getEmail(), Types.VARCHAR);
		
		super.executeUpdate(sb.toString(), params);
		
	}	
	
	
	public void atualizarCardUser(EntidadeUsuario usuario) throws Exception {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("	UPDATE COM_USUARIO SET USR_ID_CART = ?, USR_ID_ORIG = ?, USU_NOME = ?, USU_DT_NASC = ?,");
		sb.append("		USU_NOME_MAE = ?, USU_TELEFONE = ?, USU_EMAIL = ?, USU_REGDATE = SYSDATE");
		sb.append(" WHERE USU_CPF = ?");
		
		ParamDAO[] params = new ParamDAO[8];
		
		params[0] = new ParamDAO(usuario.getCartao().getUsrIdCartao(), Types.INTEGER);
		params[1] = new ParamDAO(usuario.getUsrIdOrigem() != 0 ? usuario.getUsrIdOrigem() : null, Types.INTEGER);
		params[2] = new ParamDAO(usuario.getNome(), Types.VARCHAR);
		params[3] = new ParamDAO(usuario.getDataNascimento(), Types.VARCHAR);
		params[4] = new ParamDAO(usuario.getNomeMae(), Types.VARCHAR);
		params[5] = new ParamDAO(usuario.getTelefone(), Types.VARCHAR);
		params[6] = new ParamDAO(usuario.getEmail(), Types.VARCHAR);
		params[7] = new ParamDAO(usuario.getCpf(), Types.VARCHAR);
		
		super.executeUpdate(sb.toString(), params);
	
	}
	
}
