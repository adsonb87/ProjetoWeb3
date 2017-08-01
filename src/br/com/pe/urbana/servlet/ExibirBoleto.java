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

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import br.com.pe.urbana.boleto.EmissorSegundaViaBoleto;
import br.com.pe.urbana.controller.BoletoContoller;
import br.com.pe.urbana.controller.UsuarioContoller;
import br.com.pe.urbana.entidade.EntidadeCobranca;
import br.com.pe.urbana.entidade.EntidadeUsuario;
import br.com.pe.urbana.util.Util;

@WebServlet("/exibirBoleto")
public class ExibirBoleto extends HttpServlet implements Servlet {
	
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(ExibirBoleto.class);
	
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
	
	public ExibirBoleto() {
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
				
		boolean exibir = "true".equals(request.getParameter("exibir"));
		boolean exibirBoleto = "true".equals(request.getParameter("exibirBoleto"));
		
		UsuarioContoller ctrUsuario = UsuarioContoller.getInstance();
		BoletoContoller ctrBoleto = BoletoContoller.getInstance();
		EmissorSegundaViaBoleto emissorBoleto = new EmissorSegundaViaBoleto();
		
		byte[] boletoPDF = null;
		EntidadeCobranca cobranca = null;
		EntidadeUsuario usuario = null;
				
		try{
				
			if(exibir || exibirBoleto) {
				page = "jsp/acompanhamento.jsp";
			}
			
			if(exibir) {
				
				String idCobranca = request.getParameter("idCobranca");
				cobranca = ctrBoleto.consultarBoleto(Integer.parseInt(idCobranca));
				
				if(cobranca != null) {
					
					// CONSULTA O CADASTRO DO USUÁRIO 
					usuario = ctrUsuario.consultarCpfNovo(cobranca.getCpfPagador());
					if(usuario == null) {
						usuario = ctrUsuario.consultarCpf(cobranca.getCpfPagador());
					}
					
					// GERA A SEGUNDA VIA DO BOLETO
					boletoPDF = emissorBoleto.gerarBoletoEmBytes(usuario, cobranca, request);
					
					if (boletoPDF != null && boletoPDF.length > 0) {
						response.setHeader("Content-disposition","filename=" + usuario.getNome() + ".pdf");
						response.setContentType("application/pdf");
						response.setContentLength(boletoPDF.length);
						ServletOutputStream ouputStream = response.getOutputStream();
						ouputStream.write(boletoPDF, 0, boletoPDF.length);
						ouputStream.flush();
						ouputStream.close();
					}
					
				}
						
			}
			
		} catch (Exception e) {
			
			msgAuxiliar = "Desculpe houve um problema no retorno, tente novamente!";
			msgComando = "1";
		}
		
		request.setAttribute("msgAuxiliar", msgAuxiliar);
		request.setAttribute("msgComando", msgComando);
		
		if(!exibir|| boletoPDF == null) {
			request.getRequestDispatcher(page).forward(request, response);	
		}
		
	}

}
