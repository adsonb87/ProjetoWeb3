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
import br.com.pe.urbana.controller.VinculacaoContoller;
import br.com.pe.urbana.entidade.EntidadeCartao;
import br.com.pe.urbana.entidade.EntidadeUsuario;
import br.com.pe.urbana.entidade.EntidadeVinculacao;
import br.com.pe.urbana.util.Util;

@WebServlet("/vinculacao")
public class Vinculacao extends HttpServlet implements Servlet {
	
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(Vinculacao.class);
	
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
	
	public Vinculacao() {
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String page = "jsp/vinculacao.jsp";
		String msgComando = null;
		String msgAuxiliar = null;
				
		boolean vincular = "true".equals(request.getParameter("vincular"));
		boolean cadVincular = "true".equals(request.getParameter("cadVincular"));
		
		UsuarioContoller ctrUsuario = UsuarioContoller.getInstance();
		VinculacaoContoller ctrVinculacao = VinculacaoContoller.getInstance();

		EntidadeUsuario usuario = null;
		EntidadeCartao cartao = null;
		EntidadeVinculacao vinculacao = null;
		
		try{
			
			if(cadVincular) {
				
				usuario = new EntidadeUsuario();
				cartao = new EntidadeCartao();
				
				String cpf = request.getParameter("usuCpf");
				cpf = Util.unMaskCnpj(cpf);
				usuario = ctrUsuario.consultarUsuario(cpf);
				
				String numero = request.getParameter("numCartao");
				String[] num = Util.getCartao(numero);		
				cartao.setProjeto(Integer.parseInt(num[0]));
				cartao.setDesign(Integer.parseInt(num[1]));
				cartao.setNumeroExterno(Integer.parseInt(num[2]));
				cartao.setUsuario(usuario);
				
				request.setAttribute("cartao", cartao);
				
			}
		
			if(vincular) {
				
				cartao = new EntidadeCartao();
				vinculacao = new EntidadeVinculacao();
				
				String cpf = request.getParameter("usuCpf");
				cpf = Util.unMaskCnpj(cpf);
				usuario = ctrUsuario.consultarUsuario(cpf);
				
				String numero = request.getParameter("numCartao");
				String[] num = Util.getCartao(numero);		
				cartao.setProjeto(Integer.parseInt(num[0]));
				cartao.setDesign(Integer.parseInt(num[1]));
				cartao.setNumeroExterno(Integer.parseInt(num[2]));
				
				vinculacao.setIdUsuario(usuario.getId());
				vinculacao.setCartao(cartao);
				
				ctrVinculacao.vincularCartaoUsuario(vinculacao);
				
				msgAuxiliar = "Cartão cadastrado com sucesso!";
				msgComando = "1";
				
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
