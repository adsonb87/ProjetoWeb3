package br.com.pe.urbana.gerenciarSessao;

/**
 * 
  */
public interface IntfObjetoSessao {
	//~ Metodos --------------------------------------------------------------------------------------------------------------------

	/**
	 * Realiza operações de limpeza ou manutenção no objeto que irá ser excluído do gerenciador de contexto de sessão
	 *
	 * @throws ExcecaoGerenciadorSessao
	 */
	public void limparObjeto();
}
