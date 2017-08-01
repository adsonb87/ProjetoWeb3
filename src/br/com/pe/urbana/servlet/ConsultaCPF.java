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

import br.com.pe.urbana.controller.UsuarioContoller;
import br.com.pe.urbana.entidade.EntidadeUsuario;
import br.com.pe.urbana.util.Util;

@WebServlet("/consultaCPF")
public class ConsultaCPF extends HttpServlet implements Servlet {
	
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(ConsultaCPF.class);
	
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
				
		HttpSession session = request.getSession();

		boolean consultar = "true".equals(request.getParameter("consultar"));
		boolean consCadastro = "true".equals(request.getParameter("consCadastro"));
		
		UsuarioContoller ctrUsuario = UsuarioContoller.getInstance();

		EntidadeUsuario usuario = null;
				
		try{
			
			if(consCadastro || consultar) {
				page = "jsp/consultaCPF.jsp";
			}
			
			if(consultar) {
				
				String cpf = Util.unMaskCnpj(request.getParameter("cpf"));
				
				usuario = ctrUsuario.consultarCpf(cpf);
				
				boolean flag = ctrUsuario.consultarUsuario(cpf);
				boolean flagNovo = ctrUsuario.consultarUsuarioNovo(cpf);

				if(flag || flagNovo) {
					msgAuxiliar = "você Já possui Vem Comum";
					msgComando = "1";
					
				} else if (usuario != null){
					session.setAttribute("usuario", usuario);
					
					msgAuxiliar = "Você já possui cadastro!";
					msgComando = "2";
					
				} else {					
					usuario = new EntidadeUsuario();
					
					usuario.setCpf(cpf);
					session.setAttribute("usuario", usuario);
					
					msgAuxiliar = "Você ainda não possui cadastro!";
					msgComando = "3";
					
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
