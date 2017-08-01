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
			LOG.error("Arquivo não encontrado", e);
		} catch (IOException e) {
			LOG.error("Arquivo não encontrado", e);
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
		
		String nome = null;
		byte[] boletoPDF = null;
				
		try{
			
			if(imprimir || imprimirBoleto) {
				page = "jsp/imprimirBoleto.jsp";
			}
			
			if(imprimirBoleto) {
				
				nome = (String) session.getAttribute("nome");
				boletoPDF = (byte[]) session.getAttribute("boletoPDF");
				session.removeAttribute("nome");
				session.removeAttribute("boletoPDF");
					
				if (boletoPDF != null && boletoPDF.length > 0) {
					response.setHeader("Content-disposition","attachment; filename=" + nome + ".pdf"); // o 'attachment' serve para realizar o download sem a visualização.
					//response.setHeader("Content-disposition","filename=" + nome + ".pdf");
					response.setContentType("application/pdf");
					response.setContentLength(boletoPDF.length);
					ServletOutputStream ouputStream = response.getOutputStream();
					ouputStream.write(boletoPDF, 0, boletoPDF.length);
					ouputStream.flush();
					ouputStream.close();
				}
				
			}
			
		} catch (Exception e) {
			
			msgAuxiliar = "Desculpe houve um problema no retorno, tente novamente!";
			msgComando = "1";
		}
		
		request.setAttribute("msgAuxiliar", msgAuxiliar);
		request.setAttribute("msgComando", msgComando);
		
		if(!imprimirBoleto || boletoPDF == null) {
			request.getRequestDispatcher(page).forward(request, response);	
		}
		
	}

}
