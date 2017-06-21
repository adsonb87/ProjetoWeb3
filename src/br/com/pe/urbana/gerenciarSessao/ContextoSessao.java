package br.com.pe.urbana.gerenciarSessao;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Representa um contexto de se��o que cont�m uma lista de objetos
 */
class ContextoSessao implements Serializable {
	// ~ Atributos de instancia
	// -----------------------------------------------------------------------------------------------------

	/**
	 * Representa a data e a hora que o objeto foi acessado pela �ltima vez
	 */
	private Calendar aDhUltimoAcesso;

	/**
	 * Representa a lista de objetos do contexto
	 */
	private Hashtable aObjetos;

	/**
	 * Representa o identificador do contexto
	 */
	private String aIdContextoSessao = "0";

	// ~ Construtores
	// ---------------------------------------------------------------------------------------------------------------

	/**
	 * Construtor principal da classe
	 *
	 * @param pIdContextoSessao
	 *
	 * @exception ExcecaoGerenciadorSessao
	 *                Ocorre quando o identificador do objeto est� inv�lido
	 */
	public ContextoSessao(String pIdContextoSessao) {
		int tempoExpiracao = 10;

		this.aIdContextoSessao = pIdContextoSessao;
		this.aObjetos = new Hashtable();
		this.aDhUltimoAcesso = Calendar.getInstance();
		this.aDhUltimoAcesso.add(Calendar.MINUTE, tempoExpiracao);
	}

	// ~ Metodos
	// --------------------------------------------------------------------------------------------------------------------

	/**
	 * Retorna a data do �ltimo acesso do objeto
	 *
	 * @return java.util.Calendar Retorna a data de expira��o do contexto
	 */
	public Calendar getDhUltimoAcesso() {
		return (Calendar) this.aDhUltimoAcesso.clone();
	}

	/**
	 * Retorna o identificador do contexto da sessao
	 *
	 * @return
	 */
	public String getIdContextoSessao() {
		return this.aIdContextoSessao;
	}

	/**
	 * Retorna uma Hashtable com todos os objetos do contexto de sess�o
	 *
	 * @return
	 */
	public Hashtable getObjetos() {
		return this.aObjetos;
	}

	/**
	 * Verifica se o contexto expirou com base em um tempo de validade passado
	 * como par�metro
	 *
	 * @return boolean Retorna verdadeiro se o contexto expirou
	 */
	public boolean isContextoExpirado() {
		boolean valorRetorno = false;

		Calendar dhAtual = Calendar.getInstance();
		valorRetorno = this.aDhUltimoAcesso.before(dhAtual);

		return valorRetorno;
	}

	/**
	 * Retorna o objeto de acordo com o identificador especificado
	 *
	 * @param pIdObjeto
	 *            Representa o identificador do objeto
	 *
	 * @return Object Retorna o objeto armazenado no contexto com o
	 *         identificador especificado
	 *
	 * @exception ExcecaoGerenciadorSessao
	 *                Ocorre quando o identificador do objeto est� inv�lido
	 */
	public Object getObjeto(String pIdObjeto) {
		int tempoExpiracao = 10;

		Object objetoRetorno = null;
		objetoRetorno = this.aObjetos.get(pIdObjeto);

		this.aDhUltimoAcesso = Calendar.getInstance();
		this.aDhUltimoAcesso.add(Calendar.MINUTE, tempoExpiracao);

		return objetoRetorno;
	}

	/**
	 * Adciona um objeto ao contexto da sess�o
	 *
	 * @param pIdObjeto
	 *            Representa o identificador do objeto que se deseja incluir
	 * @param pObjeto
	 *            Representa o objeto que se deseja incluir
	 *
	 * @exception ExcecaoGerenciadorSessao
	 *                Ocorre quando o identificador do objeto ou o pr�prio
	 *                objeto est� inv�lido
	 */
	public void setObjeto(String pIdObjeto, Object pObjeto) {
		int tempoExpiracao = 10;

		this.aObjetos.put(pIdObjeto, pObjeto);

		this.aDhUltimoAcesso = Calendar.getInstance();
		this.aDhUltimoAcesso.add(Calendar.MINUTE, tempoExpiracao);
	}

	/**
	 * Remove um objeto do contexto da sess�o
	 *
	 * @param pIdObjeto
	 *            Representa o identificador do objeto que se deseja incluir
	 *
	 * @exception ExcecaoGerenciadorSessao
	 *                Ocorre quando o identificador do objeto ou o pr�prio
	 *                objeto est� inv�lido
	 */
	public void removerObjeto(String pIdObjeto) {
		int tempoExpiracao = 10;

		Object objTemp = this.aObjetos.get(pIdObjeto);

		// Se o objeto implementa a interface de limpeza do gerenciador de
		// contexto, chama o m�todo de limpeza do objeto
		if (objTemp instanceof IntfObjetoSessao) {
			((IntfObjetoSessao) objTemp).limparObjeto();
		}

		this.aObjetos.remove(pIdObjeto);

		this.aDhUltimoAcesso = Calendar.getInstance();
		this.aDhUltimoAcesso.add(Calendar.MINUTE, tempoExpiracao);
	}

	/**
	 * -
	 */
	public synchronized void limpar() {
		Enumeration objetos = this.aObjetos.elements();
		Enumeration chaves = this.aObjetos.keys();
		Object objeto = null;
		Object chave = null;

		try {
			while (objetos.hasMoreElements()) {
				objeto = objetos.nextElement();
				chave = chaves.nextElement();

				if (objeto instanceof IntfObjetoSessao) {
					boolean podeLimpar = true;

					if (podeLimpar) {
						((IntfObjetoSessao) objeto).limparObjeto();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * -
	 *
	 * @return
	 */
	public String toString() {
		Object objeto = null;
		Calendar data = null;
		Enumeration objetos = this.aObjetos.elements();
		String lista = "\n >>>>C�digo do ContextoSessao - > "
				+ this.getIdContextoSessao();

		data = this.getDhUltimoAcesso();
		lista += ("\n >>>>Data de expira��o  - > " + data.getTime().toString());
		lista += "\n >>>>Lista de objetos: \n";

		while (objetos.hasMoreElements()) {
			objeto = objetos.nextElement();
			lista += ("--------- Objeto > " + objeto.toString() + " \n");
		}

		return lista;
	}
}
