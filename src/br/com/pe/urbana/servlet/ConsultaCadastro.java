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
		// Configura o Log4j com o arquivo do projeto
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
		
		String page = "jsp/consultaCadastro.jsp";
		
		String msgAuxCartao = null;
		String msgAuxUsuario = null;
		String msgAuxiliar = null;
		String msgComando = null;
				
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
			
			if (consCartao) {
				String crdSnr = request.getParameter("numeroCartao");
				cartao = ctrCartao.consultarCartao(Integer.parseInt(crdSnr));
				
				if(cartao == null) {
					msgAuxCartao = "Não encontrado";
					msgComando = "1";	
				} else if(cartao.getCpf() != null) {
					msgAuxCartao = "Já vinculado a um usuário";
					msgComando = "1";
				} else if(cartao.getMotivoBloq() != null) {
					msgAuxCartao = "Em Lista de restrição";
					msgComando = "1";
				} else {
					//msgAuxCartao = "OK!";
				}					
			}
			
			if(consCpf) {
				
				String cpf = request.getParameter("cpf");
				cpf = Util.unMaskCnpj(cpf);
				boolean flag = ctrUsuario.consultarUsuario(cpf);
				if(flag) {
					msgAuxUsuario = "Já possui Vem Comum";
					msgComando = "1";
				} else {
					//msgAuxUsuario = "OK!";
				}
						
			}
			
		} catch (Exception e) {
			
			msgAuxiliar = "Desculpe houve um problema no retorno, tente novamente!";
			msgComando = "2";
		}
		
		request.setAttribute("msgAuxCartao", msgAuxCartao);	
		request.setAttribute("msgAuxUsuario", msgAuxUsuario);
		request.setAttribute("msgAuxiliar", msgAuxiliar);
		request.setAttribute("msgComando", msgComando);
		
		request.getRequestDispatcher(page).forward(request, response);
		
	}

}
