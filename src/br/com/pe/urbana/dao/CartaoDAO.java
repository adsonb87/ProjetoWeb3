package br.com.pe.urbana.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.naming.NamingException;

import br.com.pe.urbana.entidade.EntidadeCartao;
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
	
	public ResultSet consultarCartao(EntidadeCartao cartao) throws ClassNotFoundException,
		NamingException, SQLException{
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(" SELECT ");
		sb.append("		CXU.ISS_ID PROJETO,");
		sb.append("		CXU.CD_ID DESIGN,");
		sb.append("		CXU.CRD_SNR NUMEXTERNO,");
		sb.append(" 	US.USR_ID USR_ID,");
		sb.append(" 	UDC.USRDOC_NUMBER CPF,");
		sb.append(" 	US.USR_NAME NOME,");
		sb.append(" 	UTP.USRTEL_NUMBER TELEFONE,");
		sb.append(" 	UAS.USRADD_ADDRESS LOGRADOURO,");
		sb.append(" 	UAS.USRADD_ADDNBR NUMERO,");
		sb.append(" 	UAS.USRADD_DISTRICT BAIRRO,");
		sb.append(" 	UAS.USRADD_CITY CIDADE,");
		sb.append(" 	UAS.ST_CODE UF,");
		sb.append(" 	UAS.USRADD_ZIP CEP,");
		sb.append(" 	UAS.USRADD_ADDCOMP COMPLEMENTO");
		sb.append(" FROM USERS US");
		sb.append(" INNER JOIN MERCURY.USERADDRESSES UAS ON UAS.USR_ID = US.USR_ID AND UAS.USRADD_STATUS = 'A'");
		sb.append(" LEFT JOIN MERCURY.USERTELEPHONES UTP ON UTP.USR_ID = UAS.USR_ID AND UTP.USRTEL_STATUS = 'A'");
		sb.append(" INNER JOIN USERSXUSERTYPES UST ON UST.USR_ID = US.USR_ID AND UST.UT_ID = 8 AND UST.USRUT_STATUS = 'A'");
		sb.append(" INNER JOIN USERDOCUMENTS UDC ON UDC.USR_ID = US.USR_ID AND UDC.DT_ID = 6 AND UDC.USRDOC_STATUS = 'A'");
		sb.append(" LEFT JOIN USERDOCUMENTS UDR ON UDR.USR_ID = US.USR_ID AND UDR.DT_ID = 1 AND UDR.USRDOC_STATUS = 'A'");
		sb.append(" INNER JOIN (SELECT CXU.USR_ID, MAX(CRD_SNR) CRD_SNR FROM CARDSXUSERS CXU WHERE CXU.ISS_ID = 90 AND CXU.CD_ID = 6 GROUP BY CXU.USR_ID) ULT_CARD ON ULT_CARD.USR_ID = US.USR_ID");
		sb.append(" INNER JOIN CARDSXUSERS CXU ON CXU.ISS_ID = 90 AND CXU.CD_ID = 6 AND CXU.CRD_SNR = ULT_CARD.CRD_SNR");
		sb.append(" WHERE CXU.ISS_ID = ?");
		sb.append("	AND CXU.CD_ID = ?");
		sb.append("	AND CXU.CRD_SNR = ?");
		
		ParamDAO[] params = new ParamDAO[3];
		
		params[0] = new ParamDAO(cartao.getProjeto(), Types.INTEGER);
		params[1] = new ParamDAO(cartao.getDesign(), Types.INTEGER);
		params[2] = new ParamDAO(cartao.getNumeroExterno(), Types.INTEGER);
		
		ResultSet rs = super.executeQuery(sb.toString(), params);
		
		return rs;
	}
	
	public ResultSet validarCartao(EntidadeCartao cartao) throws ClassNotFoundException,
		NamingException, SQLException{
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(" SELECT"); 
		sb.append(" 	ISS_ID PROJETO,"); 
		sb.append(" 	CD_ID DESIGN,"); 
		sb.append(" 	CRD_SNR NUMEXTERNO");
		sb.append(" FROM MERCURY.CARDS"); 
		sb.append(" WHERE ISS_ID = ?");
		sb.append("	AND CD_ID = ?");
		sb.append("	AND CRD_SNR = ?");
		
		ParamDAO[] params = new ParamDAO[3];
		
		params[0] = new ParamDAO(cartao.getProjeto(), Types.INTEGER);
		params[1] = new ParamDAO(cartao.getDesign(), Types.INTEGER);
		params[2] = new ParamDAO(cartao.getNumeroExterno(), Types.INTEGER);
		
		ResultSet rs = super.executeQuery(sb.toString(), params);
		
		return rs;
	}
	
	public void vincularCartaoUsuario(EntidadeVinculacao vinculacao) throws Exception{
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("	INSERT INTO COM_USU_CAR (CUC_USU_ID, CUC_USR_ID, CUC_ISS_ID, CUC_CD_ID, CUC_CRD_SNR)");
		sb.append("	VALUES (?, ?, ?, ?, ?)");
		
		ParamDAO[] params = new ParamDAO[5];
		
		params[0] = new ParamDAO(vinculacao.getIdUsuario(), Types.INTEGER);
		params[1] = new ParamDAO(vinculacao.getUsrId(), Types.INTEGER);
		params[2] = new ParamDAO(vinculacao.getCartao().getProjeto(), Types.INTEGER);
		params[3] = new ParamDAO(vinculacao.getCartao().getDesign(), Types.INTEGER);
		params[4] = new ParamDAO(vinculacao.getCartao().getNumeroExterno(), Types.INTEGER);

		super.executeUpdate(sb.toString(), params);
		
	}
	
}
