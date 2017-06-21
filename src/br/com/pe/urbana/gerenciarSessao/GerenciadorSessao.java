package br.com.pe.urbana.gerenciarSessao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;


/**
 * Esta classe gerencia os objetos de uma sessão web para um usuário logado, dividindo a sessão em diversos contextos
 * representados por objetos correspondentes a um fluxo de execução. Em outras palavras, para cada fluxo que necessite 
 * persistir dados na sessão, esses objetos a persistir ficarão armazenados e divididos por contexto de forma que um 
 * fluxo não sobreponha objetos de um outro fluxo. 
 * Isso é feito através da geração de um identificador único para cada contexto que é armazenado em
 * um pool. Esse código único é passado para a tela que é responsavel por repassá-lo para a(s) cadeia(s) de transação. Após a
 * abertura de outra transação outro código é gerado fazendo com que outro escopo de objetos armazenados na sessão seja requerido.
 * Com essa tecnica conseguimos resolver o problema da concorrência de interface, ou seja, agora o usuário poderá abrir, por
 * exemplo, duas ou mais janelas de browser sem que as mesmas compartilhem o(s) mesmo(s) objeto(s) de sessão.
 */
public class GerenciadorSessao implements Serializable {
	//~ Atributos/inicializadores estaticos ----------------------------------------------------------------------------------------

	/**
	 * Representa o nome que os objetos criados a partir da  classe  GerenciadorContextoSessao devem ser referenciados no
	 * ambito da sessão do  servlet container.
	 */
	public static final String ID_OBJETO = "urbana_pe";

	//~ Atributos de instancia -----------------------------------------------------------------------------------------------------

	/**
	 * Esta propriedade representa o pool de contextos de sessão
	 */
	private Hashtable aContextosSessao = null;

	/**
	 * Esta propriedade representa o pool de objetos de sessão
	 */
	private Hashtable aObjetosSessao = null;

	/**
	 * Representa o último número utilizado para a identificação do contexto
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
	 * Armazena um objeto no contexto com o Identificador especificado. Cada vez que  um objeto é adicionado é realizada
	 * uma tentativa para exclusão dos contextos com o tempo de vida ultrapassado
	 *
	 * @param pIdObjeto Representa o Identificador do contexto da sessão que se deseja recuperar o objeto
	 * @param pObjeto Representa o Identificador do objeto que será armazenado no contexto de sessão especificado
	 *
	 * @exception ExcecaoGerenciadorSessao Ocorre quando algum dos parâmetros passados para o método está inválido
	 */
	public void putObjetoSessao(String pIdObjeto, Object pObjeto){
		this.aObjetosSessao.put(pIdObjeto, pObjeto);
	}

	/**
	 * Retorna o objeto armazenado na sessao com o nome especificado. Retorna um objeto nulo caso o objeto não exista no
	 * contexto especificado. Cada vez que um objeto é recuperado é realizada uma tentativa para exclusão dos contextos com o
	 * tempo de vida ultrapassado
	 *
	 * @param pIdObjeto Representa o Identificador do contexto que se dejeta recuperar o objeto
	 *
	 * @return java.lang.Object            Retorna o objeto armazenado no contexto
	 *
	 * @exception ExcecaoGerenciadorSessao Ocorre quando algum dos parâmetros passados para o método está inválido
	 * @exception ExcecaoObjetoNaoEncontrado Ocorre quando o contexto especificado não existe na lista de contextos
	 */
	public Object getObjetoSessao(String pIdObjeto)  {
		Object objetoRetorno = null;

		objetoRetorno = this.aObjetosSessao.get(pIdObjeto);

		return objetoRetorno;
	}

	/**
	 * Remove o objeto armazenado na sessao com o nome especificado
	 *
	 * @param pIdObjeto Representa o Identificador do objeto que está na sessão
	 *
	 * @exception ExcecaoGerenciadorSessao Ocorre quando algum dos parâmetros passados para o método está inválido
	 * @exception ExcecaoObjetoNaoEncontrado Ocorre quando o contexto especificado não existe na lista de contextos
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
	 * @exception ExcecaoGerenciadorSessao Ocorre quando algum dos parâmetros passados para o método está inválido
	 */
	public boolean isObjetoSessaoValido(String pIdObjeto) {
	
		if (this.aObjetosSessao.get(pIdObjeto) == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Cria um novo contexto de sessão e retorna o seu identificador. Cada vez que um contexto é criado é realizada uma
	 * tentativa para exclusão dos contextos com o tempo de vida ultrapassado
	 *
	 * @return long                        Retorna o identificador do contexto
	 *
	 * @exception ExcecaoGerenciadorSessao Ocorre quando o identificador do contexto foi calculado de forma inválida pelo objeto
	 */
	public String criarContextoSessao() {
		this.limparContextosSessao();

		String codigo = this.getIdContextoSessao();
		ContextoSessao contexto = new ContextoSessao(codigo);
		this.aContextosSessao.put(codigo, contexto);

		return codigo;
	}

	/**
	 * Retira o contexto da sessão com  o identificador especificado. Cada vez que um contexto é excluído é realizada uma
	 * tentativa para exclusão dos contextos com o tempo de vida ultrapassado
	 *
	 * @param pIdContextoSessao - representa o identificador do contexto de objetos
	 *
	 * @exception ExcecaoGerenciadorSessao Ocorre quando o identificador do contexto passado para o método está inválido
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
	 * Retorna um Identificador para um contexto de sessão. Gera os identificadores de contexto
	 *
	 * @return String    Retorna um identificador único e válido para um contexto
	 */
	private String getIdContextoSessao() {
		this.aIdUltimoContextoGerado++;

		return (Long.toString(this.aIdUltimoContextoGerado));
	}

	/**
	 * Armazena um objeto no contexto de sessão com o Identificador especificado. Cada vez que  um objeto é adicionado é realizada
	 * uma tentativa de exclusão dos contextos com o tempo de vida ultrapassado.
	 *
	 * @param pIdContextoSessao Representa o Identificador do contexto de sessão que se deseja atualizar
	 * @param pIdObjetoContexto Representa o Identificador do objeto que será armazenado no contexto de sessão especificado
	 * @param pObjetoContexto Representa a referência para o objeto que será armazenado no contexto de sessão especificado
	 *
	 * @exception ExcecaoGerenciadorSessao Ocorre quando algum dos parâmetros passados para o método está inválido
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
	 * Retorna o objeto armazenado no contexto com o nome especificado. Retorna um objeto nulo caso o objeto não exista no
	 * contexto especificado. Cada vez que um objeto é recuperado é realizada uma tentativa para exclusão dos contextos com o
	 * tempo de vida ultrapassado
	 *
	 * @param pIdContextoSessao Representa o Identificador do contexto que se dejeta recuperar o objeto
	 * @param pIdObjetoContexto Representa o Identificador do objeto que está no contexto especificado
	 *
	 * @return java.lang.Object            Retorna o objeto armazenado no contexto
	 *
	 * @exception ExcecaoGerenciadorSessao Ocorre quando algum dos parâmetros passados para o método está inválido
	 * @exception ExcecaoObjetoNaoEncontrado Ocorre quando o contexto especificado não existe na lista de contextos
	 * @throws ExcecaoContextoSessaoNaoEncontrado
	 */
	public Object getObjetoContextoSessao(String pIdContextoSessao, String pIdObjetoContexto) {
		Object objetoRetorno = null;

		objetoRetorno = this.aContextosSessao.get(pIdContextoSessao);
		objetoRetorno = ((ContextoSessao) objetoRetorno).getObjeto(pIdObjetoContexto);
		
		return objetoRetorno;
	}

	/**
	 * Retorna uma hashtable com objetos armazenados no contexto com o id especificado. Cada vez que um objeto é recuperado
	 * é realizada uma tentativa para exclusão dos contextos com o tempo de vida ultrapassado
	 *
	 * @param pIdContextoSessao Representa o Identificador do contexto que se dejeta recuperar a hashtable de objetos
	 *
	 * @return java.lang.Hashtable Retorna os objetos armazenados no contexto
	 *
	 * @exception ExcecaoGerenciadorSessao Ocorre quando algum dos parâmetros passados para o método está inválido
	 * @exception ExcecaoObjetoNaoEncontrado Ocorre quando o contexto especificado não existe na lista de contextos
	 * @throws ExcecaoContextoSessaoNaoEncontrado
	 */
	public Hashtable getObjetosContextoSessao(String pIdContextoSessao) {
		this.limparContextosSessao();

		Object objetoRetorno = null;
		objetoRetorno = this.aContextosSessao.get(pIdContextoSessao);

		return ((ContextoSessao) objetoRetorno).getObjetos();
	}

	/**
	 * Retorna o objeto armazenado no contexto com o nome especificado. Retorna um objeto nulo caso o objeto não exista no
	 * contexto especificado. Cada vez que um objeto é recuperado é realizada uma tentativa para exclusão dos contextos com o
	 * tempo de vida ultrapassado
	 *
	 * @param pIdContextoSessao Representa o Identificador do contexto que se dejeta recuperar o objeto
	 * @param pIdObjetoContexto Representa o Identificador do objeto que está no contexto especificado
	 *
	 * @exception ExcecaoGerenciadorSessao Ocorre quando algum dos parâmetros passados para o método está inválido
	 * @exception ExcecaoObjetoNaoEncontrado Ocorre quando o contexto especificado não existe na lista de contextos
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
	 * Verifica a validade do Contexto de Sessão e em caso negativo, levanta exceção
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
	 * @exception ExcecaoGerenciadorSessao Ocorre quando algum dos parâmetros passados para o método está inválido
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
	 * Limpa todos os contextos de sessão de acordo com o ciclo de vida (time out) dos  contextos armazenados. A limpeza é
	 * feita com base na data de última limpeza e  no seu intervalo pré-definido.  Após a limpeza o método atualiza a data da
	 * última limpeza do gerenciador
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
		// Obs:	O proceso de exclusão deve ser sempre efetuado em duas etapas, 
		//		pois se varrermos a coleção e excuirmos os ítens ao mesmo tempo o índice do loop irá se perder
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
