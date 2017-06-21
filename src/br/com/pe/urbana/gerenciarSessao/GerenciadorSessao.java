package br.com.pe.urbana.gerenciarSessao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;


/**
 * Esta classe gerencia os objetos de uma sess�o web para um usu�rio logado, dividindo a sess�o em diversos contextos
 * representados por objetos correspondentes a um fluxo de execu��o. Em outras palavras, para cada fluxo que necessite 
 * persistir dados na sess�o, esses objetos a persistir ficar�o armazenados e divididos por contexto de forma que um 
 * fluxo n�o sobreponha objetos de um outro fluxo. 
 * Isso � feito atrav�s da gera��o de um identificador �nico para cada contexto que � armazenado em
 * um pool. Esse c�digo �nico � passado para a tela que � responsavel por repass�-lo para a(s) cadeia(s) de transa��o. Ap�s a
 * abertura de outra transa��o outro c�digo � gerado fazendo com que outro escopo de objetos armazenados na sess�o seja requerido.
 * Com essa tecnica conseguimos resolver o problema da concorr�ncia de interface, ou seja, agora o usu�rio poder� abrir, por
 * exemplo, duas ou mais janelas de browser sem que as mesmas compartilhem o(s) mesmo(s) objeto(s) de sess�o.
 */
public class GerenciadorSessao implements Serializable {
	//~ Atributos/inicializadores estaticos ----------------------------------------------------------------------------------------

	/**
	 * Representa o nome que os objetos criados a partir da  classe  GerenciadorContextoSessao devem ser referenciados no
	 * ambito da sess�o do  servlet container.
	 */
	public static final String ID_OBJETO = "urbana_pe";

	//~ Atributos de instancia -----------------------------------------------------------------------------------------------------

	/**
	 * Esta propriedade representa o pool de contextos de sess�o
	 */
	private Hashtable aContextosSessao = null;

	/**
	 * Esta propriedade representa o pool de objetos de sess�o
	 */
	private Hashtable aObjetosSessao = null;

	/**
	 * Representa o �ltimo n�mero utilizado para a identifica��o do contexto
	 */
	private long aIdUltimoContextoGerado = 0;

	//~ Construtores ---------------------------------------------------------------------------------------------------------------

/**
         * Construtor principal da classe
         */
	public GerenciadorSessao() {
		this.aContextosSessao = new Hashtable();
		this.aObjetosSessao = new Hashtable();
	}

	//~ Metodos --------------------------------------------------------------------------------------------------------------------

	/**
	 * Armazena um objeto no contexto com o Identificador especificado. Cada vez que  um objeto � adicionado � realizada
	 * uma tentativa para exclus�o dos contextos com o tempo de vida ultrapassado
	 *
	 * @param pIdObjeto Representa o Identificador do contexto da sess�o que se deseja recuperar o objeto
	 * @param pObjeto Representa o Identificador do objeto que ser� armazenado no contexto de sess�o especificado
	 *
	 * @exception ExcecaoGerenciadorSessao Ocorre quando algum dos par�metros passados para o m�todo est� inv�lido
	 */
	public void putObjetoSessao(String pIdObjeto, Object pObjeto){
		this.aObjetosSessao.put(pIdObjeto, pObjeto);
	}

	/**
	 * Retorna o objeto armazenado na sessao com o nome especificado. Retorna um objeto nulo caso o objeto n�o exista no
	 * contexto especificado. Cada vez que um objeto � recuperado � realizada uma tentativa para exclus�o dos contextos com o
	 * tempo de vida ultrapassado
	 *
	 * @param pIdObjeto Representa o Identificador do contexto que se dejeta recuperar o objeto
	 *
	 * @return java.lang.Object            Retorna o objeto armazenado no contexto
	 *
	 * @exception ExcecaoGerenciadorSessao Ocorre quando algum dos par�metros passados para o m�todo est� inv�lido
	 * @exception ExcecaoObjetoNaoEncontrado Ocorre quando o contexto especificado n�o existe na lista de contextos
	 */
	public Object getObjetoSessao(String pIdObjeto)  {
		Object objetoRetorno = null;

		objetoRetorno = this.aObjetosSessao.get(pIdObjeto);

		return objetoRetorno;
	}

	/**
	 * Remove o objeto armazenado na sessao com o nome especificado
	 *
	 * @param pIdObjeto Representa o Identificador do objeto que est� na sess�o
	 *
	 * @exception ExcecaoGerenciadorSessao Ocorre quando algum dos par�metros passados para o m�todo est� inv�lido
	 * @exception ExcecaoObjetoNaoEncontrado Ocorre quando o contexto especificado n�o existe na lista de contextos
	 */
	public void removerObjetoSessao(String pIdObjeto) {

		this.aObjetosSessao.remove(pIdObjeto);
	}

	/**
	 * Valida um id de objeto de sessao
	 *
	 * @param pIdObjeto Representa o Identificador do contexto que se dejeta recuperar o objeto
	 *
	 * @return java.lang.Object            Retorna o objeto armazenado no contexto
	 *
	 * @exception ExcecaoGerenciadorSessao Ocorre quando algum dos par�metros passados para o m�todo est� inv�lido
	 */
	public boolean isObjetoSessaoValido(String pIdObjeto) {
	
		if (this.aObjetosSessao.get(pIdObjeto) == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Cria um novo contexto de sess�o e retorna o seu identificador. Cada vez que um contexto � criado � realizada uma
	 * tentativa para exclus�o dos contextos com o tempo de vida ultrapassado
	 *
	 * @return long                        Retorna o identificador do contexto
	 *
	 * @exception ExcecaoGerenciadorSessao Ocorre quando o identificador do contexto foi calculado de forma inv�lida pelo objeto
	 */
	public String criarContextoSessao() {
		this.limparContextosSessao();

		String codigo = this.getIdContextoSessao();
		ContextoSessao contexto = new ContextoSessao(codigo);
		this.aContextosSessao.put(codigo, contexto);

		return codigo;
	}

	/**
	 * Retira o contexto da sess�o com  o identificador especificado. Cada vez que um contexto � exclu�do � realizada uma
	 * tentativa para exclus�o dos contextos com o tempo de vida ultrapassado
	 *
	 * @param pIdContextoSessao - representa o identificador do contexto de objetos
	 *
	 * @exception ExcecaoGerenciadorSessao Ocorre quando o identificador do contexto passado para o m�todo est� inv�lido
	 */
	public void excluirContextoSessao(String pIdContextoSessao) {
		this.limparContextosSessao();

		if (this.aContextosSessao.get(pIdContextoSessao) != null) {
			((ContextoSessao) this.aContextosSessao.get(pIdContextoSessao)).limpar();
			this.aContextosSessao.remove(pIdContextoSessao);
		}
	}

	/**
	 * -
	 *
	 * @throws ExcecaoGerenciadorSessao
	 */
	public void excluirContextosSessao() {
		Enumeration contextos = null;
		ContextoSessao contexto = null;

		// Localiza os identificadores dos objetos que expiraram
		contextos = this.aContextosSessao.elements();

		while (contextos.hasMoreElements()) {
			contexto = (ContextoSessao) contextos.nextElement();
			contexto.limpar();
		}

		this.aContextosSessao.clear();
	}

	/**
	 * Retorna um Identificador para um contexto de sess�o. Gera os identificadores de contexto
	 *
	 * @return String    Retorna um identificador �nico e v�lido para um contexto
	 */
	private String getIdContextoSessao() {
		this.aIdUltimoContextoGerado++;

		return (Long.toString(this.aIdUltimoContextoGerado));
	}

	/**
	 * Armazena um objeto no contexto de sess�o com o Identificador especificado. Cada vez que  um objeto � adicionado � realizada
	 * uma tentativa de exclus�o dos contextos com o tempo de vida ultrapassado.
	 *
	 * @param pIdContextoSessao Representa o Identificador do contexto de sess�o que se deseja atualizar
	 * @param pIdObjetoContexto Representa o Identificador do objeto que ser� armazenado no contexto de sess�o especificado
	 * @param pObjetoContexto Representa a refer�ncia para o objeto que ser� armazenado no contexto de sess�o especificado
	 *
	 * @exception ExcecaoGerenciadorSessao Ocorre quando algum dos par�metros passados para o m�todo est� inv�lido
	 * @throws ExcecaoObjetoNaoEncontrado
	 * @throws ExcecaoContextoSessaoNaoEncontrado
	 */
	public void putObjetoContextoSessao(String pIdContextoSessao, String pIdObjetoContexto, Object pObjetoContexto) {
		this.limparContextosSessao();

		ContextoSessao contexto = null;
		
		contexto = (ContextoSessao) this.aContextosSessao.get(pIdContextoSessao);

		if (contexto != null) {
			contexto.setObjeto(pIdObjetoContexto, pObjetoContexto);
		} 
	}

	/**
	 * Retorna o objeto armazenado no contexto com o nome especificado. Retorna um objeto nulo caso o objeto n�o exista no
	 * contexto especificado. Cada vez que um objeto � recuperado � realizada uma tentativa para exclus�o dos contextos com o
	 * tempo de vida ultrapassado
	 *
	 * @param pIdContextoSessao Representa o Identificador do contexto que se dejeta recuperar o objeto
	 * @param pIdObjetoContexto Representa o Identificador do objeto que est� no contexto especificado
	 *
	 * @return java.lang.Object            Retorna o objeto armazenado no contexto
	 *
	 * @exception ExcecaoGerenciadorSessao Ocorre quando algum dos par�metros passados para o m�todo est� inv�lido
	 * @exception ExcecaoObjetoNaoEncontrado Ocorre quando o contexto especificado n�o existe na lista de contextos
	 * @throws ExcecaoContextoSessaoNaoEncontrado
	 */
	public Object getObjetoContextoSessao(String pIdContextoSessao, String pIdObjetoContexto) {
		Object objetoRetorno = null;

		objetoRetorno = this.aContextosSessao.get(pIdContextoSessao);
		objetoRetorno = ((ContextoSessao) objetoRetorno).getObjeto(pIdObjetoContexto);
		
		return objetoRetorno;
	}

	/**
	 * Retorna uma hashtable com objetos armazenados no contexto com o id especificado. Cada vez que um objeto � recuperado
	 * � realizada uma tentativa para exclus�o dos contextos com o tempo de vida ultrapassado
	 *
	 * @param pIdContextoSessao Representa o Identificador do contexto que se dejeta recuperar a hashtable de objetos
	 *
	 * @return java.lang.Hashtable Retorna os objetos armazenados no contexto
	 *
	 * @exception ExcecaoGerenciadorSessao Ocorre quando algum dos par�metros passados para o m�todo est� inv�lido
	 * @exception ExcecaoObjetoNaoEncontrado Ocorre quando o contexto especificado n�o existe na lista de contextos
	 * @throws ExcecaoContextoSessaoNaoEncontrado
	 */
	public Hashtable getObjetosContextoSessao(String pIdContextoSessao) {
		this.limparContextosSessao();

		Object objetoRetorno = null;
		objetoRetorno = this.aContextosSessao.get(pIdContextoSessao);

		return ((ContextoSessao) objetoRetorno).getObjetos();
	}

	/**
	 * Retorna o objeto armazenado no contexto com o nome especificado. Retorna um objeto nulo caso o objeto n�o exista no
	 * contexto especificado. Cada vez que um objeto � recuperado � realizada uma tentativa para exclus�o dos contextos com o
	 * tempo de vida ultrapassado
	 *
	 * @param pIdContextoSessao Representa o Identificador do contexto que se dejeta recuperar o objeto
	 * @param pIdObjetoContexto Representa o Identificador do objeto que est� no contexto especificado
	 *
	 * @exception ExcecaoGerenciadorSessao Ocorre quando algum dos par�metros passados para o m�todo est� inv�lido
	 * @exception ExcecaoObjetoNaoEncontrado Ocorre quando o contexto especificado n�o existe na lista de contextos
	 * @throws ExcecaoContextoSessaoNaoEncontrado
	 */
	public void removerObjetoContextoSessao(String pIdContextoSessao, String pIdObjetoContexto) {
		this.limparContextosSessao();

		Object objetoRetorno = null;
		
		objetoRetorno = this.aContextosSessao.get(pIdContextoSessao);

		((ContextoSessao) objetoRetorno).removerObjeto(pIdObjetoContexto);
	}

	/**
	 * Valida um id de contexto de sessao
	 *
	 * @param pIdContextoSessao Representa o Identificador do contexto que se dejeta recuperar o objeto
	 *
	 * @return boolean
	 */
	public boolean isContextoSessaoValido(String pIdContextoSessao) {
		this.limparContextosSessao();

		if (pIdContextoSessao == null) {
			return false;
		}

		if (this.aContextosSessao.get(pIdContextoSessao) == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Verifica a validade do Contexto de Sess�o e em caso negativo, levanta exce��o
	 *
	 * @param pIdContextoSessao Representa o identificador do contexto que se dejeja validar
	 *
	 * @exception ExcecaoContextoSessaoExpirado
	 */
	public void validarContextoSessao(String pIdContextoSessao) {
		
	}

	/**
	 * Valida objeto dentro de um contexto de sessao
	 *
	 * @param pIdContextoSessao Representa o Identificador do contexto que se dejeta recuperar o objeto
	 * @param pIdObjetoContexto
	 *
	 * @return java.lang.Object            Retorna o objeto armazenado no contexto
	 *
	 * @exception ExcecaoGerenciadorSessao Ocorre quando algum dos par�metros passados para o m�todo est� inv�lido
	 */
	public boolean isObjetoContextoSessaoValido(String pIdContextoSessao, String pIdObjetoContexto) {
		this.limparContextosSessao();

		Object objetoRetorno = null;

		if (this.isContextoSessaoValido(pIdContextoSessao)) {
			objetoRetorno = this.aContextosSessao.get(pIdContextoSessao);

			if (objetoRetorno == null) {
				return false;
			}

			objetoRetorno = ((ContextoSessao) objetoRetorno).getObjeto(pIdObjetoContexto);

			if (objetoRetorno == null) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	/**
	 * Limpa todos os contextos de sess�o de acordo com o ciclo de vida (time out) dos  contextos armazenados. A limpeza �
	 * feita com base na data de �ltima limpeza e  no seu intervalo pr�-definido.  Ap�s a limpeza o m�todo atualiza a data da
	 * �ltima limpeza do gerenciador
	 */
	public synchronized void limparContextosSessao() {
		ContextoSessao contexto = null;
		Enumeration contextos = null;
		ArrayList chaves = new ArrayList();

		// Localiza os identificadores dos objetos que expiraram
		contextos = this.aContextosSessao.elements();

		while (contextos.hasMoreElements()) {
			contexto = (ContextoSessao) contextos.nextElement();

			if (contexto.isContextoExpirado()) {
				contexto.limpar();
				chaves.add(contexto.getIdContextoSessao());
			}
		}

		// Exclui os contextos espirados
		// Obs:	O proceso de exclus�o deve ser sempre efetuado em duas etapas, 
		//		pois se varrermos a cole��o e excuirmos os �tens ao mesmo tempo o �ndice do loop ir� se perder
		int indFim = chaves.size();

		for (int ind = 0; ind < indFim; ind++) {
			this.aContextosSessao.remove(chaves.get(ind));
		}
	}

	/**
	 * -
	 *
	 * @return
	 */
	public String toString() {
		String texto = "** Quantidade de contextos - >" + this.aContextosSessao.size();
		String lista = "\n *** Lista de Contextos ***";
		Enumeration objetos = this.aContextosSessao.elements();
		ContextoSessao contexto;

		while (objetos.hasMoreElements()) {
			contexto = (ContextoSessao) objetos.nextElement();
			lista += contexto.toString();
			lista += "\n +++++++++++++++++++++++++++";
		}
		texto += lista;

		return texto;
	}
}
