package br.com.pe.urbana.servlet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.NamingException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import br.com.pe.urbana.controller.UserContoller;
import br.com.pe.urbana.entidade.EntidadeUser;
import br.com.pe.urbana.util.Util;

@WebServlet("/login")
public class Login extends HttpServlet implements Servlet {
	
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(Login.class);
	
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
	
	public Login() {
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String page = "jsp/inicio.jsp";
		String msgComando = null;
		String msgAuxiliar = null;
			
		HttpSession session = request.getSession();

		boolean consLogin = "true".equals(request.getParameter("consLogin"));

		String login = Util.unMaskCnpj(request.getParameter("login"));
		String password = request.getParameter("password");
		EntidadeUser user = null;
		
		try{
			
			if(consLogin) {
		
				user = validateLogin(login, password);
				
				if(user == null) {
					msgAuxiliar = "Login/Senha inválido!";
					msgComando = "1";
				} else {
					msgComando = "2";
					session.setAttribute("user", user);
				}
			}
			
		} catch (Exception e) {
			
			msgAuxiliar = "Desculpe houve um problema no retorno, tente novamente!";
			msgComando = "3";
		}
		
		request.setAttribute("msgAuxiliar", msgAuxiliar);
		request.setAttribute("msgComando", msgComando);
		
		request.getRequestDispatcher(page).forward(request, response);
		
	}
	
	private EntidadeUser validateLogin(String login, String password)
			throws ClassNotFoundException, NamingException, SQLException, IOException {

		if (login == null || password == null) {
			return null;
		}
		
		UserContoller ctr = UserContoller.getInstance();
		EntidadeUser user = ctr.validaLogin(login);

		if (user == null) {
			return null;
		}

		if (!user.getPassword().equals(password.trim())) {
			return null;
		}

		return user;
	}

}
