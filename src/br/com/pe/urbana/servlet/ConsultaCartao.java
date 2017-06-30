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
import br.com.pe.urbana.entidade.EntidadeCartao;
import br.com.pe.urbana.util.Util;

@WebServlet("/consultaCartao")
public class ConsultaCartao extends HttpServlet implements Servlet {
	
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(ConsultaCartao.class);
	
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
	
	public ConsultaCartao() {
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
		
		CartaoContoller controller = CartaoContoller.getInstance();

		EntidadeCartao cartao = null;
		
//		try{
//			
//			if(consultar || consCartao) {
//				page = "jsp/consultaCartao.jsp";
//			}
//		
//			if(consultar) {
//				cartao = new EntidadeCartao();
//				
//				String numCartao = request.getParameter("numero");
//				
//				String[] num = Util.getCartao(numCartao);
//						
//				cartao.setProjeto(Integer.parseInt(num[0]));
//				cartao.setDesign(Integer.parseInt(num[1]));
//				cartao.setNumeroExterno(Integer.parseInt(num[2]));
//				
//				EntidadeCartao card = null;
//				
//				if(cartao.getProjeto() != 90 || cartao.getDesign() != 06) {
//					
//					msgAuxiliar = "Cartão inválido";
//					msgComando = "1";
//					
//				} else {
//					
//					boolean flag = controller.validarCartao(cartao);
//					if(flag) {
//						
//						card = controller.consultarCartao(cartao);
//						
//						if(card != null) {
//							
//							msgAuxiliar = "Cartão já está vinculado a um CPF!";
//							msgComando = "1";
//						
//						} else {
//							
//							msgAuxiliar = "Cartão ainda não está vinculado!";
//							msgComando = "2";
//							request.setAttribute("numCartao", numCartao);	
//						}
//						
//					} else {
//						
//						msgAuxiliar = "Cartão não encontrado, Tente novamente!";
//						msgComando = "1";
//					}
//				}
//				
//			}
//			
//		} catch (Exception e) {
//			
//			msgAuxiliar = "Desculpe houve um problema no retorno, tente novamente!";
//			msgComando = "1";
//		}
		
		request.setAttribute("msgAuxiliar", msgAuxiliar);
		request.setAttribute("msgComando", msgComando);
		
		request.getRequestDispatcher(page).forward(request, response);
		
	}

}
