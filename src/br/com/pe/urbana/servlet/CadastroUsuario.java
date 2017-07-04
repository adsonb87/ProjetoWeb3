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
import br.com.pe.urbana.entidade.EntidadeCartao;
import br.com.pe.urbana.entidade.EntidadeUsuario;
import br.com.pe.urbana.util.Util;

@WebServlet("/cadastroUsuario")
public class CadastroUsuario extends HttpServlet implements Servlet {
	
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(CadastroUsuario.class);
	
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
	
	public CadastroUsuario() {
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String page = "jsp/cadastroUsuario.jsp";
		String msgComando = null;
		String msgAuxiliar = null;
			
		HttpSession session = request.getSession();
		
		boolean cadastrar = "true".equals(request.getParameter("cadastrar"));
		boolean consCadastro = "true".equals(request.getParameter("consCadastro"));
		boolean altCadastro = "true".equals(request.getParameter("altCadastro"));
		
		CartaoContoller ctrCartao = CartaoContoller.getInstance();

		EntidadeUsuario usuario = null;
		EntidadeCartao cartao = null;
	
		try{
			
			if(cadastrar || consCadastro || altCadastro) {
				page = "jsp/cadastroUsuario.jsp";
			}
			
			if(consCadastro) {
				
				usuario = (EntidadeUsuario)session.getAttribute("usuario");
				
				request.setAttribute("usuario", usuario);
				session.removeAttribute("usuario");
				
			} else if(cadastrar) {
				
				usuario = new EntidadeUsuario();
				
				String usrIdOrigem = request.getParameter("usrIdOrigem");
				String cpf = request.getParameter("cpf");
					   cpf = Util.unMaskCnpj(cpf);
				String nome = request.getParameter("nome");
				String dataNascimento = request.getParameter("dataNascimento");
				String nomeMae = request.getParameter("nomeMae");
				String telefone = request.getParameter("telefone");
					   telefone = Util.getTelefone(telefone);
			    String email = request.getParameter("email");

				usuario.setUsrIdOrigem(Integer.parseInt(usrIdOrigem));
				usuario.setCpf(cpf);
				usuario.setNome(nome);
				usuario.setDataNascimento(dataNascimento);
				usuario.setNomeMae(nomeMae);
				usuario.setTelefone(telefone);
				usuario.setEmail(email);
				
				
				String crdSnr = request.getParameter("crdSnr");
				String numCartao = request.getParameter("numCartao");
				
				cartao = ctrCartao.consultarCartao(crdSnr);
				cartao.setNumCartao(numCartao);
				
				usuario.setCartao(cartao);

				session.setAttribute("usuario", usuario);
				msgAuxiliar = "Cadastro realizado com sucesso";
				msgComando = "1";
			
			} else if(altCadastro) {
				
				usuario = new EntidadeUsuario();
				
				String usrIdOrigem = request.getParameter("usrIdOrigem");
				String cpf = request.getParameter("cpf");
					   cpf = Util.unMaskCnpj(cpf);
				String nome = request.getParameter("nome");
				String dataNascimento = request.getParameter("dataNascimento");
				String nomeMae = request.getParameter("nomeMae");
				String telefone = request.getParameter("telefone");
					   telefone = Util.getTelefone(telefone);
			    String email = request.getParameter("email");
				String crdSnr = request.getParameter("crdSnr");
				String numCartao = request.getParameter("numCartao");
			    
				usuario.setUsrIdOrigem(Integer.parseInt(usrIdOrigem));
				usuario.setCpf(cpf);
				usuario.setNome(nome);
				usuario.setDataNascimento(dataNascimento);
				usuario.setNomeMae(nomeMae);
				usuario.setTelefone(telefone);
				usuario.setEmail(email);
				
				cartao = ctrCartao.consultarCartao(crdSnr);
				cartao.setNumCartao(numCartao);
				usuario.setCartao(cartao);
				
				request.setAttribute("usuario", usuario);
				msgComando = "2";
				
			}
			
		} catch (Exception e) {
			
			msgAuxiliar = "Desculpe houve um problema no retorno, tente novamente!";
			msgComando = "3";
		}
		
		request.setAttribute("msgAuxiliar", msgAuxiliar);
		request.setAttribute("msgComando", msgComando);
		
		request.getRequestDispatcher(page).forward(request, response);
		
	}

}
