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

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import br.com.pe.urbana.controller.UsuarioContoller;
import br.com.pe.urbana.entidade.EntidadeEndereco;
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
		
		String page = "jsp/inicio.jsp";
		String msgComando = null;
		String msgAuxiliar = null;
				
		boolean cadastrar = "true".equals(request.getParameter("cadastrar"));
		boolean consCadastro = "true".equals(request.getParameter("consCadastro"));
		//boolean atualCadastro = "true".equals(request.getParameter("atualCadastro"));
		
		UsuarioContoller controller = UsuarioContoller.getInstance();

		EntidadeUsuario usuario = null;
		EntidadeEndereco endereco = null;
		
		String numCartao = request.getParameter("numCartao");
	
		try{
			
			if(cadastrar || consCadastro) {
				page = "jsp/cadastroUsuario.jsp";
				request.setAttribute("numCartao", numCartao);
			}
			
			if(consCadastro) {
				
				String usuCpf = request.getParameter("usuCpf");
				usuCpf = Util.unMaskCnpj(usuCpf);

				usuario = controller.consultarUsuario(usuCpf);
				
				if(usuario != null) {
					request.setAttribute("usuario", usuario);
				} else {
					usuario = new EntidadeUsuario();
					usuario.setCpf(usuCpf);
					request.setAttribute("usuario", usuario);
				}
				
			} else if(cadastrar) {
				
				usuario = new EntidadeUsuario();
				endereco = new EntidadeEndereco();
				
				String usrId = request.getParameter("usrId");
				String nome = request.getParameter("nome");
				String cpf = request.getParameter("cpf");
					   cpf = Util.unMaskCnpj(cpf);
			    String telefone = request.getParameter("telefone");
			    String email = request.getParameter("email");
				String cep = request.getParameter("cep").replaceAll("-", "");
				String logradouro = request.getParameter("logradouro");
				String bairro = request.getParameter("bairro");
				String cidade = request.getParameter("cidade");
				String uf = request.getParameter("uf");
				String complemento = request.getParameter("complemento");
				String numero = request.getParameter("numero");
				
				endereco.setCep(cep);
				endereco.setLogradouro(logradouro);
				endereco.setBairro(bairro);
				endereco.setCidade(cidade);
				endereco.setUf(uf);
				endereco.setComplemento(complemento);
				endereco.setNumero(numero);
				
				usuario.setUsrId(Integer.parseInt(usrId));
				usuario.setNome(nome);
				usuario.setCpf(cpf);
				usuario.setTelefone(telefone);
				usuario.setEmail(email);
				usuario.setEndereco(endereco);
				
				//Caso não exista na tabela COM_COMUM
				boolean flag = controller.validarNovoUsuario(cpf);
				if(!flag) {
					controller.cadastrarUsuario(usuario);
				} else {
					controller.atualizarUsuario(usuario);
				}
				
				//Caso Usuário não possua cartão, direcione para outra página
				if(numCartao != null) {
					msgAuxiliar = "Cadastro Realizado com Sucesso!";
					msgComando = "1";
					request.setAttribute("usuCpf", cpf);
				} else {
					msgAuxiliar = "Dirija-se ao VEM";
					msgComando = "2";
				}
			
			}
			
			List<String> cidades = controller.getCidades();
			request.setAttribute("lcidade", cidades);
			
		} catch (Exception e) {
			
			msgAuxiliar = "Desculpe houve um problema no retorno, tente novamente!";
			msgComando = "1";
		}
		
		request.setAttribute("numCartao", numCartao);
		request.setAttribute("msgAuxiliar", msgAuxiliar);
		request.setAttribute("msgComando", msgComando);
		
		request.getRequestDispatcher(page).forward(request, response);
		
	}

}
