package br.com.pe.urbana.servlet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import br.com.pe.urbana.controller.CartaoContoller;
import br.com.pe.urbana.controller.UsuarioContoller;
import br.com.pe.urbana.entidade.EntidadeCartao;
import br.com.pe.urbana.entidade.EntidadeUsuario;
import br.com.pe.urbana.util.Util;

@WebServlet("/consultaCadastro")
public class ConsultaCadastro extends HttpServlet implements Servlet {
	
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(ConsultaCadastro.class);
	
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
	
	public ConsultaCadastro() {
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String page = "jsp/inicio.jsp";
		
		String msgAuxCartao = null;
		String msgAuxUsuario = null;
		String msgAuxiliar = null;
		String msgComando = null;

		HttpSession session = request.getSession();
	
		boolean consCpf = "true".equals(request.getParameter("consCpf"));
		boolean consCartao = "true".equals(request.getParameter("consCartao"));
		boolean consCadastro = "true".equals(request.getParameter("consCadastro"));
		
		CartaoContoller ctrCartao = CartaoContoller.getInstance();
		UsuarioContoller ctrUsuario = UsuarioContoller.getInstance();

		EntidadeCartao cartao = null;
		EntidadeUsuario usuario = null;
		
		try{
			
			if(consCadastro || consCpf || consCartao) {
				page = "jsp/consultaCadastro.jsp";
			}
			
			String numCartao = request.getParameter("numeroCartao");
			String crdSnr = Util.getCrdSnr(numCartao);
			
			String cpf = request.getParameter("cpf");
			cpf = Util.unMaskCnpj(cpf);
			
			boolean card = false;
			boolean user = false;
			
			if (consCartao) {
				EntidadeCartao cardAux = null;
				
				cartao = ctrCartao.consultarCartao(crdSnr);
				
				//SE O CARTÃO FOI VINCULADO NO DIA CORRENTE(COM_USUARIO)
				if(cartao != null) {
					cardAux = ctrCartao.consultarCartaoVinculado(cartao.getUsrIdCartao());
					
					if(cartao.getCpf() != null || cardAux != null) {
						msgAuxCartao = "Já vinculado a um usuário";
						msgComando = "1";
					} else if(cartao.getMotivoBloq() != null) {
						msgAuxCartao = "Em Lista de restrição";
						msgComando = "1";
					} else {
						card = true;
					}
					
				} else {
					msgAuxCartao = "Não encontrado";
					msgComando = "1";
				}				
			}
			
			if(consCpf) {
	
				boolean flag = ctrUsuario.consultarUsuario(cpf);
				boolean flagNovo = ctrUsuario.consultarUsuarioNovo(cpf);

				if(flag || flagNovo) {
					msgAuxUsuario = "Já possui Vem Comum";
					msgComando = "1";
				} else {
					user = true;
				}
			
			}
			
			if(card || user) {
				
				cartao = ctrCartao.consultarCartao(crdSnr);
				usuario = ctrUsuario.consultarCpf(cpf);
				
				//CASO USUARIO TENHA CADASTRO NO MERCURY
				//DAR PRIORIDADE AO COMANDO ANTERIOR
				if(usuario != null && msgComando == null) {
	
					msgComando = "2";
					
					cartao.setNumCartao(numCartao);	
					usuario.setCartao(cartao);					
					session.setAttribute("usuario", usuario);
				
				//CASO USUARIO NÃO TENHA CADASTRO
				//DAR PRIORIDADE AO COMANDO ANTERIOR
				} else if(msgComando == null) {
					
					msgAuxUsuario = "Você ainda não possui cadastro!";
					msgComando = "3";
					
					usuario = new EntidadeUsuario();
					
					cartao.setNumCartao(numCartao);	
					usuario.setCartao(cartao);
					usuario.setCpf(cpf);
					session.setAttribute("usuario", usuario);
					
				}
				
			}
			
		} catch (Exception e) {
			
			msgAuxiliar = "Desculpe houve um problema no retorno, tente novamente!";
			msgComando = "4";
		}
		
		request.setAttribute("msgAuxCartao", msgAuxCartao);	
		request.setAttribute("msgAuxUsuario", msgAuxUsuario);
		request.setAttribute("msgComando", msgComando);
		request.setAttribute("msgAuxiliar", msgAuxiliar);
		
		request.getRequestDispatcher(page).forward(request, response);
		
	}

}
