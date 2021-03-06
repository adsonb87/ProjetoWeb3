package br.com.pe.urbana.servlet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import br.com.pe.urbana.entidade.EntidadeCobranca;
import br.com.pe.urbana.entidade.EntidadeUsuario;
import br.com.pe.urbana.util.Util;

@WebServlet("/imprimirBoleto")
public class ImprimirBoleto extends HttpServlet implements Servlet {
	
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(ImprimirBoleto.class);
	
	static {
		// CONFIGURA O LOG4J COM O ARQUIVO DO PROJET
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(Util.PATH));
		} catch (FileNotFoundException e) {
			LOG.error("Arquivo n�o encontrado", e);
		} catch (IOException e) {
			LOG.error("Arquivo n�o encontrado", e);
		}
		PropertyConfigurator.configure(props);
	}
	
	public ImprimirBoleto() {
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

		boolean imprimir = "true".equals(request.getParameter("imprimir"));
		boolean imprimirBoleto = "true".equals(request.getParameter("imprimirBoleto"));
		
		EntidadeUsuario usuario = null;
		String nome = null;
		byte[] boletoPDF = null;
				
		try{
			
			if(imprimir || imprimirBoleto) {
				page = "jsp/imprimirBoleto.jsp";
				
				usuario = (EntidadeUsuario) session.getAttribute("usuario");
				session.removeAttribute("usuario");
				request.setAttribute("usuario", usuario);
				
				EntidadeCobranca cobranca = (EntidadeCobranca) session.getAttribute("cobranca");
				session.removeAttribute("cobranca");
				request.setAttribute("cobranca", cobranca);

			}
			
			if(imprimirBoleto) {
				nome = request.getParameter("nome");
				boletoPDF = (byte[]) session.getAttribute("boletoPDF");
				session.removeAttribute("boletoPDF");
					
				if (boletoPDF != null && boletoPDF.length > 0) {
					imprimirWeb(response, boletoPDF, nome);
				} else {
					// CASO O USUARIO TENTE GERAR NOVAMENTE O BOLETO
					msgComando = "1";
				}
			}
			
		} catch (Exception e) {
			
			msgAuxiliar = "Desculpe houve um problema no retorno, tente novamente!";
			msgComando = "2";
		}
		
		request.setAttribute("msgAuxiliar", msgAuxiliar);
		request.setAttribute("msgComando", msgComando);
		
		if(!imprimirBoleto || boletoPDF == null) {
			request.getRequestDispatcher(page).forward(request, response);	
		}		
	}
	
	private static void imprimirWeb(HttpServletResponse response, byte[] boletoPDF, String nome) throws Exception {
		response.setHeader("Content-disposition","attachment; filename=" + nome + ".pdf"); // o 'attachment' serve para realizar o download sem a visualiza��o.
		//response.setHeader("Content-disposition","filename=" + nome + ".pdf");
		response.setContentType("application/pdf");
		response.setContentLength(boletoPDF.length);
		ServletOutputStream ouputStream = response.getOutputStream();
		ouputStream.write(boletoPDF, 0, boletoPDF.length);
		ouputStream.flush();
		ouputStream.close();
	}

}
