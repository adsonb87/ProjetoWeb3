package br.com.pe.urbana.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.naming.NamingException;

import br.com.pe.urbana.entidade.EntidadeCobranca;
import br.com.pe.urbana.factory.ConnectionFactoryDB;
import br.com.pe.urbana.factory.util.ParamDAO;
import br.com.pe.urbana.util.Util;

public class BoletoDao extends ConnectionFactoryDB {
	
	private static BoletoDao instance;

	private BoletoDao() {

	}

	public static BoletoDao getInstance() {

		if (instance == null) {
			instance = new BoletoDao();
		}

		return instance;
	}
	
	public void salvarCobranca(EntidadeCobranca cobranca) throws Exception {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("	INSERT INTO COM_REGISTRO_PAGAMENTO (CPF_PAGADOR, RP_DTVCTO, RP_DTPROCESS, STATUS_ID,");
		sb.append("		RP_VALOR, RP_NN, RP_REGUSER, RP_REGDATE)");
		sb.append("	VALUES (?, ?, ?, ?, ?, ?, ?, SYSDATE)");
		
		ParamDAO[] params = new ParamDAO[7];
		
		params[0] = new ParamDAO(Util.unMaskCnpj(cobranca.getCpfPagador()), Types.VARCHAR);
		params[1] = new ParamDAO(Util.formatDate(cobranca.getDataVencimento()), Types.VARCHAR);
		params[2] = new ParamDAO(Util.formatDate(cobranca.getDataProcessamento()), Types.VARCHAR);
		params[3] = new ParamDAO(cobranca.getStatus().getValor(), Types.INTEGER);
		params[4] = new ParamDAO(cobranca.getValor().doubleValue(), Types.DOUBLE);
		params[5] = new ParamDAO(cobranca.getNossoNumero(), Types.INTEGER);
		params[6] = new ParamDAO(cobranca.getRegUser(), Types.VARCHAR);
		
		super.executeUpdate(sb.toString(), params);
		
	}
	
	public ResultSet getNossoNumero() throws ClassNotFoundException, NamingException, SQLException {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(" SELECT NN_ID, NN_SEQINI, NN_SEQFIN, NN_SEQVALOR");
		sb.append(" FROM COM_NOSSONUMERO");
		sb.append("	WHERE NN_ID = 1");
		sb.append("		AND NN_STATUS = 'A'");
		
		ResultSet rs = super.executeQuery(sb.toString());
		
		return rs;
	
	}
	
	public void atualizarNossoNumero(Integer nossoNumero) throws Exception {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("	UPDATE COM_NOSSONUMERO SET NN_SEQVALOR = ?, NN_REGDATE = SYSDATE");
		sb.append(" WHERE NN_ID = 1");
		
		ParamDAO[] params = new ParamDAO[1];
		
		params[0] = new ParamDAO(nossoNumero + 1, Types.INTEGER); 
		
		super.executeUpdate(sb.toString(), params);
	
	}
	
	public ResultSet consultarBoleto(Integer id) throws ClassNotFoundException, NamingException, SQLException {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("	SELECT ");
		sb.append(" 	RP.RP_ID,");
		sb.append("  	US.USU_CPF,");
		sb.append("  	US.USU_NOME,");
		sb.append("  	RP.RP_NN,");
		sb.append("  	RP.RP_DTVCTO,");
		sb.append("  	RP.RP_DTPROCESS,");
		sb.append("  	ST.ST_ID,");
		sb.append("  	ST.ST_DESCRICAO,");                                        
		sb.append("  	RP.RP_VALOR,");                                            
		sb.append("  	RP.RP_DTPAG,");
		sb.append("  	RP.RP_DTCRED,");
		sb.append("  	RP.RP_REGUSER,");                                     
		sb.append("  	RP.RP_REGDATE");                                        
		sb.append("	FROM COM_REGISTRO_PAGAMENTO RP");                           
		sb.append("	INNER JOIN COM_USUARIO US ON US.USU_CPF = RP.CPF_PAGADOR");    
		sb.append("	INNER JOIN COM_STATUS ST ON ST.ST_ID = RP.STATUS_ID");       
		sb.append("	WHERE RP_ID = ?");                                         
		
		ParamDAO[] params = new ParamDAO[1];
		
		params[0] = new ParamDAO(id, Types.INTEGER);
		
		ResultSet rs = super.executeQuery(sb.toString(), params);
		
		return rs;
	
	}

	public ResultSet consultarRegistro(String cpf) throws ClassNotFoundException, NamingException, SQLException {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(" SELECT *");
		sb.append(" FROM COM_REGISTRO_PAGAMENTO ");
		sb.append(" WHERE CPF_PAGADOR = ? ");
		
		ParamDAO[] params = new ParamDAO[1];
		
		params[0] = new ParamDAO(Util.unMaskCnpj(cpf), Types.VARCHAR);
		
		ResultSet rs = super.executeQuery(sb.toString(), params);
		
		return rs;
	
	}
	
	public ResultSet listarCobrancas(String cpf) throws ClassNotFoundException, NamingException, SQLException {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(" SELECT ");
		sb.append("		RP.RP_ID,");
		sb.append("  	CPF_PAGADOR,");
		sb.append("		US.USU_NOME,");
		sb.append("  	RP.RP_NN,");
		sb.append("  	RP.RP_DTVCTO,");
		sb.append("  	RP.RP_DTPROCESS,");
		sb.append("  	ST.ST_ID,");
		sb.append(" 	ST.ST_DESCRICAO,");
		sb.append(" 	RP.RP_VALOR,");
		sb.append("  	RP.RP_DTPAG,");
		sb.append("  	RP.RP_DTCRED,");
		sb.append(" 	RP.RP_REGUSER,");
		sb.append(" 	RP.RP_REGDATE");
		sb.append("	FROM COM_REGISTRO_PAGAMENTO RP");
		sb.append("	INNER JOIN COM_USUARIO US ON US.USU_CPF = RP.CPF_PAGADOR");
		sb.append("	INNER JOIN COM_STATUS ST ON ST.ST_ID = RP.STATUS_ID");
		sb.append(" WHERE RP.CPF_PAGADOR = ?");
		sb.append("	ORDER BY RP.RP_NN DESC");
		
		ParamDAO[] params = new ParamDAO[1];
		
		params[0] = new ParamDAO(Util.unMaskCnpj(cpf), Types.VARCHAR);
		
		ResultSet rs = super.executeQuery(sb.toString(), params);
		
		return rs;
	
	}
	
}
