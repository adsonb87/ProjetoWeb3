package br.com.pe.urbana.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.naming.NamingException;

import br.com.pe.urbana.entidade.EntidadeCartao;
import br.com.pe.urbana.entidade.EntidadeUsuario;
import br.com.pe.urbana.entidade.EntidadeVinculacao;
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
	
	public ResultSet consultarCartao(int crdSnr) throws ClassNotFoundException,
		NamingException, SQLException{
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(" SELECT ");
		sb.append(" 	CXU.USR_ID,");
		sb.append(" 	CASE WHEN CXU.CRD_SNR = CM.CRD_SNR THEN '1' ELSE '2' END VIA_CARTAO,");
		sb.append(" 	HR.HLR_ID MOT_BLOQ,");
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

		params[0] = new ParamDAO(crdSnr, Types.INTEGER);
		
		ResultSet rs = super.executeQuery(sb.toString(), params);
		
		return rs;
	}
	
}
