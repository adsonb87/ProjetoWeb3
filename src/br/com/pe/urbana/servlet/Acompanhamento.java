package br.com.pe.urbana.servlet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
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

import br.com.pe.urbana.controller.BoletoContoller;
import br.com.pe.urbana.controller.UsuarioContoller;
import br.com.pe.urbana.entidade.EntidadeCobranca;
import br.com.pe.urbana.entidade.EntidadeUser;
import br.com.pe.urbana.entidade.EntidadeUsuario;
import br.com.pe.urbana.util.Util;

@WebServlet("/acompanhamento")
public class Acompanhamento extends HttpServlet implements Servlet {
	
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(Acompanhamento.class);
	
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
	
	public Acompanhamento() {
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
		
		boolean acompanhar = "true".equals(request.getParameter("acompanhar"));
		
		BoletoContoller ctrBoleto = BoletoContoller.getInstance();
		UsuarioContoller ctrUsuario = UsuarioContoller.getInstance();
		
		try{
			
			if(acompanhar) {
				page = "jsp/acompanhamento.jsp";
				
				EntidadeUser user = (EntidadeUser) session.getAttribute("user");
				session.removeAttribute("user");
				request.setAttribute("servlet", "acompanhamento");
				
				// CONSULTA O USUÁRIO 
				EntidadeUsuario usuario = null;
				usuario = ctrUsuario.consultarCpfNovo(user.getLogin());
				if(usuario == null) {
					usuario = ctrUsuario.consultarCpf(user.getLogin());
				}
							
				request.setAttribute("usuario", usuario);
				
				List<EntidadeCobranca> lEntidade = ctrBoleto.listarCobrancas(user.getLogin());
				session.setAttribute("lEntidade", lEntidade);
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
