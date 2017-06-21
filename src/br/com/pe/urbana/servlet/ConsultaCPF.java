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

import br.com.pe.urbana.controller.UsuarioContoller;
import br.com.pe.urbana.entidade.EntidadeUsuario;
import br.com.pe.urbana.util.Util;

@WebServlet("/consultaCPF")
public class ConsultaCPF extends HttpServlet implements Servlet {
	
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(ConsultaCPF.class);
	
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
	
	public ConsultaCPF() {
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String page = "jsp/inicio.jsp";
		String msgComando = null;
		String msgAuxiliar = null;
				
		boolean consultar = "true".equals(request.getParameter("consultar"));
		boolean consCartao = "true".equals(request.getParameter("consCartao"));
		boolean consultarCadastro = "true".equals(request.getParameter("consultarCadastro"));
		
		UsuarioContoller controller = UsuarioContoller.getInstance();

		EntidadeUsuario usuario = null;
		
		String numero = request.getParameter("numCartao");
		
		try{
			
			if(consultar || consCartao || consultarCadastro) {
				page = "jsp/consultaCPF.jsp";
				request.setAttribute("numCartao", numero);
			}
		
			if(consultar) {
				
				String cpf = request.getParameter("cpf");
				cpf = Util.unMaskCnpj(cpf);
				usuario = controller.consultarUsuario(cpf);
				
				if(usuario != null) {
					
					msgAuxiliar = "Você já possui cadastro!";
					msgComando = "1";
					
					request.setAttribute("usuCpf", usuario.getCpf());
					request.setAttribute("numCartao", numero);
					
				} else {
					
					msgAuxiliar = "Você ainda possui cadastro!";
					msgComando = "2";
					
					request.setAttribute("usuCpf", cpf);
					request.setAttribute("numCartao", numero);
					
				}
				
			}
			
		} catch (Exception e) {
			
			msgAuxiliar = "Desculpe houve um problema no retorno, tente novamente!";
			msgComando = "1";
		}
		
		request.setAttribute("msgAuxiliar", msgAuxiliar);
		request.setAttribute("msgComando", msgComando);
		
		request.getRequestDispatcher(page).forward(request, response);
		
	}

}
