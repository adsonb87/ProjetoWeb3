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
import br.com.pe.urbana.entidade.EntidadeUsuario;
import br.com.pe.urbana.util.Util;

@WebServlet("/solicitarCartao")
public class SolicitarCartao extends HttpServlet implements Servlet {
	
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(SolicitarCartao.class);
	
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
	
	public SolicitarCartao() {
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String page = "jsp/solicitarCartao.jsp";
		String msgComando = null;
		String msgAuxiliar = null;
				
		boolean consCadastro = "true".equals(request.getParameter("consCadastro"));
		boolean solicitar = "true".equals(request.getParameter("solicitar"));
		
		UsuarioContoller ctrUsuario = UsuarioContoller.getInstance();
		CartaoContoller ctrCartao = CartaoContoller.getInstance();
		
		EntidadeUsuario usuario = null;
		
		String numCartao = request.getParameter("numCartao");
		
		try{
			
			if(consCadastro || solicitar) {
				page = "jsp/solicitarCartao.jsp";
				request.setAttribute("numCartao", numCartao);
			}
			
			if(consCadastro) {
				
				String cpf = request.getParameter("usuCpf");
				cpf = Util.unMaskCnpj(cpf);
				//usuario = ctrUsuario.consultarUsuario(cpf);
				
				//Caso não exista no mercury
				if(usuario == null) {
					usuario = ctrUsuario.consultarNovoUsuario(cpf);
				}
				
				request.setAttribute("usuario", usuario);
				
			}
		
			if(solicitar) {
				
				usuario = new EntidadeUsuario();
				
				String idUsuario = request.getParameter("idUsuario");
				String usrId = request.getParameter("usrId");
				
				usuario.setId(Integer.parseInt(idUsuario));
				usuario.setUsrIdOrigem(Integer.parseInt(usrId));
				
				//ctrCartao.solicitarCartao(usuario);
				
				msgAuxiliar = "Cartão solicitado com sucesso!";
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
