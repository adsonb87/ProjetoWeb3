package br.com.pe.urbana.gerenciarSessao;

/**
 * 
  */
public interface IntfObjetoSessao {
	//~ Metodos --------------------------------------------------------------------------------------------------------------------

	/**
	 * Realiza opera��es de limpeza ou manuten��o no objeto que ir� ser exclu�do do gerenciador de contexto de sess�o
	 *
	 * @throws ExcecaoGerenciadorSessao
	 */
	public void limparObjeto();
}
