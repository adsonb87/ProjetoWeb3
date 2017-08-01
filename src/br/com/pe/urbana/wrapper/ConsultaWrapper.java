package br.com.pe.urbana.wrapper;

import org.displaytag.decorator.TableDecorator;

import br.com.pe.urbana.entidade.EntidadeCobranca;

public class ConsultaWrapper extends TableDecorator {

	public String getStatusCobranca() {
		Object obj = this.getCurrentRowObject();
		String situacao = "";
		Integer status;
		if (obj instanceof EntidadeCobranca) {
			EntidadeCobranca entidade = (EntidadeCobranca) obj;
			status = entidade.getStatus().getId();
			if (status != null) {
				switch (status) {
				case 1:
				case 2:
				case 4:
					situacao = "<p class=\"pendente\">Pendente</p>";
					break;
				case 3:
					situacao = "<p class=\"pago\">Pago</p>";
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