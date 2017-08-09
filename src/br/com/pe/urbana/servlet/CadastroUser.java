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

import br.com.pe.urbana.controller.BoletoContoller;
import br.com.pe.urbana.controller.UserContoller;
import br.com.pe.urbana.controller.UsuarioContoller;
import br.com.pe.urbana.entidade.EntidadeUser;
import br.com.pe.urbana.util.Util;

@WebServlet("/cadastroUser")
public class CadastroUser extends HttpServlet implements Servlet {
	
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(CadastroUser.class);
	
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
	
	public CadastroUser() {
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
					
		boolean cadastrar = "true".equals(request.getParameter("cadastrar"));
		boolean cadastrarUser = "true".equals(request.getParameter("cadastrarUser"));	

		HttpSession session = request.getSession();

		UsuarioContoller ctrUsuario = UsuarioContoller.getInstance();
		BoletoContoller ctrBoleto = BoletoContoller.getInstance();
		UserContoller ctrUser = UserContoller.getInstance();
		
		EntidadeUser user = null;
		
		try{
			
			if(cadastrar || cadastrarUser) {
				page = "jsp/cadastroUser.jsp";
			}
			
			if(cadastrar) {
				
				String cpf = Util.unMaskCnpj(request.getParameter("cpf"));
				String password = request.getParameter("password1");
				
				// CONSULTA SE O USUÁRIO POSSUI CADASTRO
				boolean flagUsuario = ctrUsuario.consUsuario(cpf);
				boolean flagUsuarioNovo = ctrUsuario.consultarUsuarioNovo(cpf);
				// CONSULTA SE O USUÁRIO POSSUI ALGUM REGISTRO DE PAGAMENTO
				boolean flagRegistro = ctrBoleto.consultarRegistro(cpf);
				
				// SO PODERÁ CADASTRAR USER, SE TIVER ALGUM REGISTRO DE PAGAMENTO (BOLETO)
				if((flagUsuario || flagUsuarioNovo) && flagRegistro) {
					
					user = new EntidadeUser();
					user.setLogin(cpf);
					user.setPassword(password);
					user.setRegUser("SISTEMA");
					
					boolean flagUser = ctrUser.consultaUser(user);
					// CONSULTA SE O LOGIN JÁ POSSUI UMA CONTA
					if(!flagUser) {
						
						ctrUser.cadastrarUser(user);
						session.setAttribute("user", user);
						msgAuxiliar = "Cadastro realizado com sucesso";
						msgComando = "1";
					} else {
						
						msgAuxiliar = "Este usuário já possui uma conta";
						msgComando = "2";
					}
				} else {
					
					msgAuxiliar = "Você não possui cadastro na nossa base de dados";
					msgComando = "2";
				}	
			}
			
		} catch (Exception e) {
			
			msgAuxiliar = "Desculpe houve um problema no retorno, tente novamente!";
			msgComando = "2";
		}
		
		request.setAttribute("msgAuxiliar", msgAuxiliar);
		request.setAttribute("msgComando", msgComando);
		
		request.getRequestDispatcher(page).forward(request, response);
		
	}

}
