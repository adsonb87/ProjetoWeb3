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

@WebServlet("/vinculacao")
public class Vinculacao extends HttpServlet implements Servlet {
	
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(Vinculacao.class);
	
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
	
	public Vinculacao() {
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

		boolean vincular = "true".equals(request.getParameter("vincular"));
		boolean cadVincular = "true".equals(request.getParameter("cadVincular"));
		
		CartaoContoller ctrCartao = CartaoContoller.getInstance();
		UsuarioContoller ctrUsuario = UsuarioContoller.getInstance();
		
		EntidadeUsuario usuario = null;
		EntidadeCartao cartao = null;
		
		try{
			
			if(vincular || cadVincular) {
				page = "jsp/vinculacao.jsp";
			}
			
			if(cadVincular) {
				
				usuario = (EntidadeUsuario)session.getAttribute("usuario");
				
				request.setAttribute("usuario", usuario);
				session.removeAttribute("usuario");
				
			}
		
			if(vincular) {
				
				cartao = new EntidadeCartao();
				usuario = new EntidadeUsuario();
				
				String usrIdCartao = request.getParameter("usrIdCartao");
				String usrIdOrigem = request.getParameter("usrIdOrigem");
				String cpf = Util.unMaskCnpj(request.getParameter("cpf"));
				String nome = request.getParameter("nome");
				String dataNascimento = request.getParameter("dataNascimento");
				String nomeMae = request.getParameter("nomeMae");
				String telefone = Util.unMaskTelefone(request.getParameter("telefone"));
				String email = request.getParameter("email");
				
				cartao.setUsrIdCartao(Integer.parseInt(usrIdCartao));
				usuario.setCartao(cartao);
				usuario.setUsrIdOrigem(Integer.parseInt(usrIdOrigem));
				usuario.setCpf(cpf);
				usuario.setNome(nome);
				usuario.setDataNascimento(dataNascimento);
				usuario.setNomeMae(nomeMae);
				usuario.setTelefone(telefone);
				usuario.setEmail(email);
				
				boolean flag = ctrUsuario.consultarUsuarioNovo(cpf);
				
				if(!flag){
					ctrCartao.vincularCardUser(usuario);
					msgAuxiliar = "Cartão cadastrado com sucesso!";
					msgComando = "1";
				} else {
					msgAuxiliar = "Cartão já foi cadastrado";
					msgComando = "1";
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
