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

import br.com.pe.urbana.dao.BoletoDao;
import br.com.pe.urbana.entidade.EntidadeCobranca;
import br.com.pe.urbana.entidade.EntidadeNossoNumero;
import br.com.pe.urbana.entidade.Status;
import br.com.pe.urbana.util.Util;

public class BoletoControllerRep {
	
	private static final Logger LOG = Logger.getLogger(BoletoControllerRep.class);
	
	private static BoletoControllerRep instance;

	private BoletoControllerRep() {

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
	
    public static BoletoControllerRep getInstance() {

		if (instance == null) {
			instance = new BoletoControllerRep();
		}

		return instance;
	}
    
    public void salvarCobranca(EntidadeCobranca cobranca) throws Exception {

    	BoletoDao dao = BoletoDao.getInstance();
		dao.salvarCobranca(cobranca);
	}
    
    public EntidadeNossoNumero getNossoNumero() throws ClassNotFoundException, 
		NamingException, SQLException {
    	
    	BoletoDao dao = BoletoDao.getInstance();
    	ResultSet rs = dao.getNossoNumero();
    	EntidadeNossoNumero nossoNumero = null;

		if ((rs != null) && rs.next()) {
			nossoNumero = new EntidadeNossoNumero();
			
			nossoNumero.setId(rs.getInt("NN_ID"));
			nossoNumero.setSeqInicial(rs.getInt("NN_SEQINI"));
			nossoNumero.setSeqFinal(rs.getInt("NN_SEQFIN"));
			nossoNumero.setSeqValor(rs.getInt("NN_SEQVALOR"));
			
		}
		
		return nossoNumero;
	}
    
    public void atualizarNossoNumero(Integer nossoNumero) throws Exception {
    	
    	BoletoDao dao = BoletoDao.getInstance();
		dao.atualizarNossoNumero(nossoNumero);
    }
    
    public EntidadeCobranca consultarBoleto(Integer id) throws ClassNotFoundException, 
		NamingException, SQLException {
    	
    	BoletoDao dao = BoletoDao.getInstance();
    	ResultSet rs = dao.consultarBoleto(id);
    	EntidadeCobranca cobranca = null;

		if ((rs != null) && rs.next()) {
			cobranca = new EntidadeCobranca();
			Status status = new Status();
			
			cobranca.setId(rs.getInt("RP_ID"));
			cobranca.setCpfPagador(rs.getString("USU_CPF"));
			cobranca.setNome(rs.getString("USU_NOME"));
			cobranca.setNossoNumero(rs.getInt("RP_NN"));
			cobranca.setDataVencimento(rs.getDate("RP_DTVCTO"));
			cobranca.setDataProcessamento(rs.getDate("RP_DTPROCESS"));
			status.setId(rs.getInt("ST_ID"));
			status.setDescricao(rs.getString("ST_DESCRICAO"));
			cobranca.setStatus(status);
			cobranca.setValor(rs.getBigDecimal("RP_VALOR"));
			cobranca.setDataPagamento(rs.getDate("RP_DTPAG"));
			cobranca.setDataCredito(rs.getDate("RP_DTCRED"));
			cobranca.setRegUser(rs.getString("RP_REGUSER"));
			cobranca.setRegDate(rs.getDate("RP_REGDATE"));
			
		}
		
		return cobranca;
	}
    
    public boolean consultarRegistro(String cpf) throws ClassNotFoundException, 
		NamingException, SQLException {
		
		BoletoDao dao = BoletoDao.getInstance();
		ResultSet rs = dao.consultarRegistro(cpf);	
		boolean flag = false;
		
		if ((rs != null) && rs.next()) {
			flag = true;
		}
		
		return flag;
	}
    
    public List<EntidadeCobranca> listarCobrancas(String cpf) throws ClassNotFoundException, 
		NamingException, SQLException {
	
		BoletoDao dao = BoletoDao.getInstance();
		ResultSet rs = dao.listarCobrancas(cpf);
	
		List<EntidadeCobranca> lista = null;
		
		if (rs != null) {
			lista = new ArrayList<EntidadeCobranca>();
			while (rs.next()) {
				
				EntidadeCobranca cobranca = new EntidadeCobranca();
				Status status = new Status();
				
				cobranca.setId(rs.getInt("RP_ID"));
				cobranca.setCpfPagador(rs.getString("CPF_PAGADOR"));
				cobranca.setNome(rs.getString("USU_NOME"));
				cobranca.setNossoNumero(rs.getInt("RP_NN"));
				cobranca.setDataVencimento(rs.getDate("RP_DTVCTO"));
				cobranca.setDataProcessamento(rs.getDate("RP_DTPROCESS"));
				status.setId(rs.getInt("ST_ID"));
				status.setDescricao(rs.getString("ST_DESCRICAO"));
				cobranca.setStatus(status);
				cobranca.setValor(rs.getBigDecimal("RP_VALOR"));
				cobranca.setDataPagamento(rs.getDate("RP_DTPAG"));
				cobranca.setDataCredito(rs.getDate("RP_DTCRED"));
				cobranca.setRegUser(rs.getString("RP_REGUSER"));
				cobranca.setRegDate(rs.getDate("RP_REGDATE"));
				
				lista.add(cobranca);
			}
		
		}
		
		return lista;
}
    
}
