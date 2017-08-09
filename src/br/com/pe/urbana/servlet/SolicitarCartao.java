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

import br.com.pe.urbana.boleto.EmissorBoleto;
import br.com.pe.urbana.controller.UsuarioContoller;
import br.com.pe.urbana.entidade.EntidadeEndereco;
import br.com.pe.urbana.entidade.EntidadeUsuario;
import br.com.pe.urbana.util.Util;

@WebServlet("/solicitarCartao")
public class SolicitarCartao extends HttpServlet implements Servlet {
	
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(SolicitarCartao.class);
	
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
	
	public SolicitarCartao() {
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

		boolean confirmar = "true".equals(request.getParameter("confirmar"));
		boolean solicitar = "true".equals(request.getParameter("solicitar"));
		
		//CartaoContoller ctrCartao = CartaoContoller.getInstance();
		UsuarioContoller ctrUsuario = UsuarioContoller.getInstance();
		
		EntidadeUsuario usuario = null;
		EntidadeEndereco endereco = null;
		byte[] boletoPDF = null;
		
		try{
			
			if(confirmar || solicitar) {
				page = "jsp/solicitarCartao.jsp";
			}
			
			if(confirmar) {
				
				usuario = (EntidadeUsuario)session.getAttribute("usuario");
				
				request.setAttribute("usuario", usuario);
				session.removeAttribute("usuario");
				
			}
		
			if(solicitar) {
				
				usuario = new EntidadeUsuario();
				endereco = new EntidadeEndereco();
				
				String usrIdOrigem = request.getParameter("usrIdOrigem");
				String cpf = request.getParameter("cpf");
				String cpfFormat = Util.unMaskCnpj(cpf);
				String nome = request.getParameter("nome");
				String dataNascimento = request.getParameter("dataNascimento");
				String nomeMae = request.getParameter("nomeMae");
				String telefone = request.getParameter("telefone");
					   telefone = Util.unMaskTelefone(telefone);
			    String email = request.getParameter("email");
				String cep = request.getParameter("cep").replaceAll("-", "");
				String logradouro = request.getParameter("logradouro");
				String bairro = request.getParameter("bairro");
				String cidade = request.getParameter("cidade");
				String uf = request.getParameter("uf");
				String complemento = request.getParameter("complemento");
				String numero = request.getParameter("numero");

				usuario.setUsrIdOrigem(Integer.parseInt(usrIdOrigem));
				usuario.setCpf(cpfFormat);
				usuario.setNome(nome);
				usuario.setDataNascimento(dataNascimento);
				usuario.setNomeMae(nomeMae);
				usuario.setTelefone(telefone);
				usuario.setEmail(email);
				endereco.setCep(cep);
				endereco.setLogradouro(logradouro);
				endereco.setBairro(bairro);
				endereco.setCidade(cidade);
				endereco.setUf(uf);
				endereco.setComplemento(complemento);
				endereco.setNumero(numero);
				usuario.setEndereco(endereco);
				usuario.setRegUser("SISTEMA");
								
				boolean flag = ctrUsuario.consultarUsuarioNovo(cpfFormat);
				
				if(!flag){
					
					EmissorBoleto emissorBoleto = new EmissorBoleto();
					// GERA O BOLETO, SALVA O USUÁRIO E A COBRAÇA
					boletoPDF = emissorBoleto.gerarBoletoEmBytes(usuario, request);
					
					session.setAttribute("boletoPDF", boletoPDF);
					session.setAttribute("usuario", usuario);
												
					msgComando = "1";
				} else {
					
					msgAuxiliar = "Cadastre uma senha para acompanhar o status do pedido";
					msgComando = "2";
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
	
	
	
}
