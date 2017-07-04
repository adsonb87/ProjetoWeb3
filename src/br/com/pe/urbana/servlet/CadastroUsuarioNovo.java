package br.com.pe.urbana.servlet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
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

import br.com.pe.urbana.controller.UsuarioContoller;
import br.com.pe.urbana.entidade.Endereco;
import br.com.pe.urbana.entidade.EntidadeUsuario;
import br.com.pe.urbana.util.Util;

@WebServlet("/cadastroUsuarioNovo")
public class CadastroUsuarioNovo extends HttpServlet implements Servlet {
	
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(CadastroUsuarioNovo.class);
	
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
	
	public CadastroUsuarioNovo() {
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
		
		boolean cadastrar = "true".equals(request.getParameter("cadastrar"));
		boolean altCadastro = "true".equals(request.getParameter("altCadastro"));
		boolean novoCadastro = "true".equals(request.getParameter("novoCadastro"));
		
		UsuarioContoller ctrUsuario = UsuarioContoller.getInstance();

		EntidadeUsuario usuario = null;
		Endereco endereco = null;
	
		try{
			
			if(cadastrar || altCadastro || novoCadastro) {
				page = "jsp/cadastroUsuarioNovo.jsp";
			}
			
			if(altCadastro) {
				
				usuario = (EntidadeUsuario)session.getAttribute("usuario");
				
				request.setAttribute("usuario", usuario);
				session.removeAttribute("usuario");

				
			} else if(novoCadastro) {
				
				usuario = (EntidadeUsuario)session.getAttribute("usuario");
				
				request.setAttribute("usuario", usuario);
				session.removeAttribute("usuario");

				
			} else if(cadastrar) {
				
				usuario = new EntidadeUsuario();
				endereco = new Endereco();
				
				String usrIdOrigem = request.getParameter("usrIdOrigem");
				String cpf = request.getParameter("cpf");
					   cpf = Util.unMaskCnpj(cpf);
				String nome = request.getParameter("nome");
				String dataNascimento = request.getParameter("dataNascimento");
				String nomeMae = request.getParameter("nomeMae");
				String telefone = request.getParameter("telefone");
					   telefone = Util.getTelefone(telefone);
			    String email = request.getParameter("email");
				String cep = request.getParameter("cep").replaceAll("-", "");
				String logradouro = request.getParameter("logradouro");
				String bairro = request.getParameter("bairro");
				String cidade = request.getParameter("cidade");
				String uf = request.getParameter("uf");
				String complemento = request.getParameter("complemento");
				String numero = request.getParameter("numero");

				usuario.setUsrIdOrigem(Integer.parseInt(usrIdOrigem));
				usuario.setCpf(cpf);
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
				
				ctrUsuario.cadastrarUsuarioNovo(usuario);

				msgAuxiliar = "Cadastro realizado com sucesso";
				msgComando = "1";
			
			}
			
			List<String> cidades = ctrUsuario.getCidades();
			request.setAttribute("lcidade", cidades);
			
		} catch (Exception e) {
			
			msgAuxiliar = "Desculpe houve um problema no retorno, tente novamente!";
			msgComando = "1";
		}
		
		request.setAttribute("msgAuxiliar", msgAuxiliar);
		request.setAttribute("msgComando", msgComando);
		
		request.getRequestDispatcher(page).forward(request, response);
		
	}

}
