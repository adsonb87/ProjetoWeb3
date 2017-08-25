package br.com.pe.urbana.wrapper;

import org.displaytag.decorator.TableDecorator;

import br.com.pe.urbana.entidade.EntidadeCobranca;

public class ConsultaWrapper extends TableDecorator {

	public String getStatusCobranca() {
		
		Object obj = this.getCurrentRowObject();
		String situacao = "";
		Integer status = null;
		if (obj instanceof EntidadeCobranca) {
			EntidadeCobranca entidade = (EntidadeCobranca) obj;
			status = entidade.getStatus().getValor();
			if (status != null) {
				switch (status) {
				case 1:
				case 4:
					situacao = "<span class='pendente glyphicon glyphicon-question-sign' title='Pendente de Pagamento'></span>";
					break;
				case 2:
					situacao = "<span class='registrado glyphicon glyphicon-exclamation-sign' title='Registrado'></span>";
					break;
				case 3:
					situacao = "<span class='pago glyphicon glyphicon-ok-sign' title='Pago'></span>";
					break;
				}
			}
		}
		return situacao;
	}

	public String getExibirBoleto() throws Exception {

		Object obj = this.getCurrentRowObject();
		String funcao = "";
		Integer idCobranca = null;
		String msgRetorno = "";
		if (obj instanceof EntidadeCobranca) {
			EntidadeCobranca ent = (EntidadeCobranca) obj;
			idCobranca = ent.getId();
					
			funcao = "href ='javascript:visualizarBoleto(\"" + idCobranca + "\")'";
			msgRetorno = "<a class=\"lupa-exibir" + "\"" + funcao + "><span class='glyphicon glyphicon-search'></span> </a>";
		}

		return msgRetorno;
	}
}