package br.com.pe.urbana.dao;

import java.sql.Types;

import br.com.pe.urbana.entidade.EntidadeVinculacao;
import br.com.pe.urbana.factory.ConnectionFactoryDB;
import br.com.pe.urbana.factory.util.ParamDAO;

public class VinculacaoDAO extends ConnectionFactoryDB {
	
	private static VinculacaoDAO instance;

	private VinculacaoDAO() {

	}

	public static VinculacaoDAO getInstance() {

		if (instance == null) {
			instance = new VinculacaoDAO();
		}

		return instance;
	}
	
	public void vincularCartaoUsuario(EntidadeVinculacao vinculacao) throws Exception{
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("	INSERT INTO COM_USU_CAR (CUC_USU_ID, CUC_ISS_ID, CUC_CD_ID, CUC_CRD_SNR)");
		sb.append("	VALUES (?, ?, ?, ?)");
		
		ParamDAO[] params = new ParamDAO[4];
		
		params[0] = new ParamDAO(vinculacao.getIdUsuario(), Types.INTEGER);
		params[1] = new ParamDAO(vinculacao.getCartao().getProjeto(), Types.INTEGER);
		params[2] = new ParamDAO(vinculacao.getCartao().getDesign(), Types.INTEGER);
		params[3] = new ParamDAO(vinculacao.getCartao().getNumeroExterno(), Types.INTEGER);

		super.executeUpdate(sb.toString(), params);
		
	}
	
}
