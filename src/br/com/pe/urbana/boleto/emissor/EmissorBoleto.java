package br.com.pe.urbana.boleto.emissor;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.google.gson.Gson;

import br.com.caelum.stella.boleto.Banco;
import br.com.caelum.stella.boleto.Beneficiario;
import br.com.caelum.stella.boleto.Boleto;
import br.com.caelum.stella.boleto.Datas;
import br.com.caelum.stella.boleto.Desconto;
import br.com.caelum.stella.boleto.Endereco;
import br.com.caelum.stella.boleto.InstrucaoCobranca;
import br.com.caelum.stella.boleto.Juros;
import br.com.caelum.stella.boleto.Multa;
import br.com.caelum.stella.boleto.Pagador;
import br.com.caelum.stella.boleto.RecebimentoDivergente;
import br.com.caelum.stella.boleto.bancos.Itau;
import br.com.caelum.stella.boleto.bancos.gerador.GeradorDeDigitoPadrao;
import br.com.caelum.stella.boleto.json.GeradorJson;
import br.com.caelum.stella.boleto.transformer.GeradorDeBoleto;
import br.com.pe.urbana.boleto.RetornoRegistro;
import br.com.pe.urbana.controller.BoletoContoller;
import br.com.pe.urbana.controller.UsuarioContoller;
import br.com.pe.urbana.entidade.EntidadeCobranca;
import br.com.pe.urbana.entidade.EntidadeNossoNumero;
import br.com.pe.urbana.entidade.EntidadeUsuario;
import br.com.pe.urbana.entidade.Status;
import net.sf.jasperreports.engine.util.JRLoader;

public class EmissorBoleto {
	
	public byte[] gerarBoleto(EntidadeUsuario usuario, HttpServletRequest request) throws Exception {
		
		byte[] pdfBytes = null;
		
		HttpSession session = request.getSession();
		
		// CRIA O BOLETO
		Boleto boleto = criarBoleto(usuario);
		
		// CARREGA O CAMINHO FÍSICO DOS ARQUIVOS
		String template = request.getServletContext().getRealPath("/WEB-INF/jasper/boleto-urbana.jasper");
		String template_sub = request.getServletContext().getRealPath("/WEB-INF/jasper/boleto-urbana_instrucoes.jasper");
		String logoUrbana = request.getServletContext().getRealPath("/WEB-INF/jasper/logoUrbana.png");
		
		// MAPA PARA PARÂMETROS
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("logo_urbana", logoUrbana);
		parametros.put("SUB_INSTRUCOES", JRLoader.loadObjectFromFile(template_sub));
		
		// CARREGA O CONTEÚDO DO ARQUIVO EM UM INPUTSTREAM
		InputStream templateBoleto = new FileInputStream(template);

		// PASSA PARA O GERADOR DE BOLETO O DADOS DO TEMPLATE NO CONSTRUTOR,
		// JUNTO COM O MAPA DE PARÂMETROS E O BOLETO
		GeradorDeBoleto gerador = new GeradorDeBoleto(templateBoleto, parametros, boleto);
		pdfBytes = gerador.geraPDF();
		
		// FAZ O REGISTRO DO BOLETO
		RetornoRegistro retornoRegistro = registrar(boleto);
//		RetornoRegistro retornoRegistro = new RetornoRegistro();
//		retornoRegistro.setStatus(200);

		if(pdfBytes != null && retornoRegistro.getStatus().equals(200)) {
			
			UsuarioContoller ctrUsuario = UsuarioContoller.getInstance();
			BoletoContoller ctrBoleto = BoletoContoller.getInstance();
			
			EntidadeCobranca cobranca = new EntidadeCobranca();
			
			cobranca.setCpfPagador(boleto.getPagador().getDocumento());
			cobranca.setNossoNumero(Integer.parseInt(boleto.getBeneficiario().getNossoNumero()));
			cobranca.setDataVencimento(boleto.getDatas().getVencimento().getTime());
			cobranca.setDataProcessamento(boleto.getDatas().getProcessamento().getTime());
			cobranca.setValor(boleto.getValorCobrado());
			cobranca.setStatus(Status.REGISTRADO);
			cobranca.setRegUser("VEM COMUM");
			
			// SALVA O USUÁRIO, A COBRAÇA (ATUALIZA O NOSSO NÚMERO)
			ctrUsuario.cadastrarUsuarioNovo(usuario);
			ctrBoleto.salvarCobranca(cobranca);
			
			// VAI SER UTILIZADO NA TELA IMPRIMIR_BOLETO
			session.setAttribute("cobranca", cobranca);
			
			return pdfBytes;
		
		} else if(!retornoRegistro.getStatus().equals(200)) {
			
			session.setAttribute("retornoRegistro", retornoRegistro);
			
		}
		return null;
	}
	
	private static Boleto criarBoleto(EntidadeUsuario usuario) throws Exception {
		
		// EXTRAINDO DADOS DO USUÁRIO PARA O PAGADOR
		Endereco enderecoPagador = Endereco.novoEndereco()
				.comLogradouro(usuario.getEndereco().getLogradouro() + ", " + usuario.getEndereco().getNumero())
				.comBairro(usuario.getEndereco().getBairro())
        		.comCep(usuario.getEndereco().getCep())
        		.comCidade(usuario.getEndereco().getCidade())
        		.comUf(usuario.getEndereco().getUf());
	        
		Pagador pagador = Pagador.novoPagador()
				.comNome(usuario.getNome())
                .comDocumento(usuario.getCpfFormatado())
                .comEndereco(enderecoPagador);
		
		Beneficiario beneficiario = criarBeneficiario();
		
		// DATA DE VENCIMENTO DO BOLETO
		Calendar dataVencimento = Calendar.getInstance();
		dataVencimento.add(Calendar.DAY_OF_MONTH, 30);
		
		Datas datas = Datas.novasDatas()
                .comDocumento(Calendar.getInstance())
                .comProcessamento(Calendar.getInstance())
                .comVencimento(dataVencimento);

		Juros juros = Juros.novoJuros()
				.comTipo(5); // ISENTO
        
		Multa multa = Multa.novaMulta()
        		.comTipo(3); // ISENTO
        
		Desconto desconto = Desconto.novoDesconto()
        		.comTipo(0); // SEM DESCONTO
        
        RecebimentoDivergente recebimentoDivergente = RecebimentoDivergente.novoRecebimentoDivergente()
        		.comTipoAutorizacao("3"); // QUANDO O TÍTULO NÃO DEVE ACEITAR PAGAMENTO DE VALORES DIVERGENTES AO DA COBRANÇA

        InstrucaoCobranca instrucaoCobranca1 = InstrucaoCobranca.novaInstrucaoCobranca()
        		.comInstrucao("90"); // NO VENCIMENTO, PAGÁVEL EM QUALQUER AGÊNCIA BANCÁRIA
        
        Banco banco = new Itau();

        Boleto boleto = Boleto.novoBoleto()
        		.comEspecieMoeda("R$")
        		.comCodigoEspecieMoeda(9)
        		.comTipoAmbiente(1) // PARA TESTE
        		.comTipoRegistro(1) // PARA REGISTRO
        		.comTipoCobranca(1) // PARA BOLETOS
        		.comTipoProduto("00006")
        		.comSubProduto("00008")
        		.comAceite(false) // BOLETO PROPOSTA (FALSE = N)
        		.comEspecie("05") // RECIBO (RC)
                .comEspecieDocumento("RC") // RECIBO (05)
        		.comTipoPagamento(1) // PAGAMENTO REALIZADO À VISTA
        		.comIndicadorPagamentoParcial(false) // NÃO ACEITA PAGAMENTO PARCIAL
        		.comJuros(juros)
        		.comMulta(multa)
        		.comDesconto(desconto)
        		.comRecebimentoDivergente(recebimentoDivergente)
        		.comInstrucaoCobranca1(instrucaoCobranca1)
                .comBanco(banco)
                .comDatas(datas)
                .comBeneficiario(beneficiario)
                .comPagador(pagador)
                .comValorBoleto("5.00")
                .comNumeroDoDocumento(beneficiario.getNossoNumero())
                .comInstrucoes("SR. CAIXA, FAVOR RECEBER O BOLETO MESMO APÓS A DATA DE VENCIMENTO, SEM", "COBRANÇA DE MULTA E JUROS.", "PAGAMENTO APENAS EM DINHEIRO.")
                .comLocaisDePagamento("Até o vencimento, preferencialmente no Itaú / Após o vencimento, somente no Itaú");
    
		return boleto;
   	}

	private static Beneficiario criarBeneficiario() throws Exception {

		// RESPONSÁVEL PELA GERAÇÃO DO DIGITO VERIFICADOR
		GeradorDeDigitoPadrao geradorDV = new GeradorDeDigitoPadrao();

		// PEGA O VALOR ATUAL DO NOSSO NÚMERO NO BANCO 
		EntidadeNossoNumero nossoNumero = getNossoNumero();
		
		Endereco enderecoBeneficiario = Endereco.novoEndereco()
        		.comLogradouro("AV GOV AGAMENON MAGALHAES")
        		.comBairro("BOA VISTA")
        		.comCep("50070-160")
        		.comCidade("RECIFE")
        		.comUf("PE");

        Beneficiario beneficiario = Beneficiario.novoBeneficiario()
                .comNomeBeneficiario("SIND DAS EMP DE TRANSP DE PASSAG DO EST DE PERNAM")
                .comDocumento("09.759.606/0001-80")
                .comAgencia("2938")
                .comDigitoAgencia("4")
                .comCodigoBeneficiario("28439")
                .comDigitoCodigoBeneficiario("1")
                .comCarteira(109)
                .comEndereco(enderecoBeneficiario)
                .comNossoNumero(nossoNumero.getSeqValorFormatado());
                
        //GERANDO DV DO NOSSO NÚMERO
        String bloco = beneficiario.getAgencia()
        		.concat(beneficiario.getCodigoBeneficiario())
        		.concat(String.valueOf(beneficiario.getCarteira()))
        		.concat(beneficiario.getNossoNumero());
        int dv = geradorDV.geraDigitoBloco1(bloco);
        
        beneficiario.comDigitoNossoNumero(String.valueOf(dv));
        
		return beneficiario;
	}
	
	// RESPONSÁVEL POR PEGAR O NOSSO NÚMERO
	private static EntidadeNossoNumero getNossoNumero() throws Exception {
		
		BoletoContoller ctr = BoletoContoller.getInstance();
		EntidadeNossoNumero nossoNumero = null;
		
		nossoNumero = ctr.getNossoNumero();
		
		return nossoNumero;
	}
	
	private static String autentica() throws Exception {
		
		String accessToken = null;
		String url = "https://oauth.itau.com.br/identity/connect/token";
		String clientId = "wF3uNeLfw4Or0";
		String clientSecret = "roiKd8GL4JhflnR5o5mJX4crVOy6ROqW5zdY69YpZHXd97YZd96jjiQ5dKkOiwM2yz4wK5-GQLsZs9dK_rlXZA2";
		String contentType = "application/x-www-form-urlencoded";
		String scope = "readonly";
		String grantType = "client_credentials";
		
		//CONVERTE PARA BASE 64
		String credentials = clientId + ":" + clientSecret;
		String headerValue = "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());
		
		HttpClient cliente = HttpClients.createDefault();
		HttpPost request = new HttpPost(url);
		
	    request.addHeader("Authorization", headerValue);
		request.addHeader("Content-Type", contentType);
		
		List<NameValuePair> valores = new ArrayList<NameValuePair>();
		valores.add(new BasicNameValuePair("scope", scope));
		valores.add(new BasicNameValuePair("grant_type", grantType));
		request.setEntity(new UrlEncodedFormEntity(valores));

		HttpResponse response = cliente.execute(request);
		
		int status = response.getStatusLine().getStatusCode();
		if (status == HttpStatus.SC_OK){
			//PEGANDO O CONTEUDO DO RESPONSE
			HttpEntity conteudo = response.getEntity();
			String conteudoString = EntityUtils.toString(conteudo, "UTF-8");

			//CONVERTE O CONTEUDO DO RESPONSE PARA O JSON
			JSONObject json = new JSONObject(conteudoString);
			accessToken = json.getString("access_token");
		}
		
		return accessToken;
	}
	
	private static RetornoRegistro registrar(Boleto boleto) throws Exception {
		
		String token = autentica();
		RetornoRegistro retornoRegistro = null;
		
		// GERA O JSON DO BOLETO
		JsonObject jsonBoleto = GeradorJson.gerarJson(boleto);

		if(token != null && jsonBoleto != null) {
			
			String url = "https://gerador-boletos.itau.com.br/router-gateway-app/public/codigo_barras/registro";
			String accept = "application/vnd.itau";
			String itauChave = "9a6a013b-54df-49a5-bf99-f674761f5775";
			String contentType = "application/json";
			String identificador = "09759606000180";
			
			HttpClient cliente = HttpClients.createDefault();
			HttpPost request = new HttpPost(url);
			
			StringEntity params = new StringEntity(jsonBoleto.toString());
			
			request.addHeader("Accept", accept);
			request.addHeader("access_token", token);
			request.addHeader("itau-chave", itauChave);
			request.addHeader("Content-Type", contentType);
			request.addHeader("identificador", identificador);
			request.setEntity(params);
			
			HttpResponse response = cliente.execute(request);
			
			//PEGANDO O CONTEUDO DO RESPONSE
			HttpEntity conteudo = response.getEntity();
			String conteudoString = EntityUtils.toString(conteudo, "UTF-8");
			
			//USADO PARA CONVERTER O CONTEUDO DO RESPONSE
			Gson gson = new Gson();
						
			//CONVERTE JSON PARA CLASS
			retornoRegistro = gson.fromJson(conteudoString, RetornoRegistro.class);
			retornoRegistro.setStatus(response.getStatusLine().getStatusCode());
		}
		
		return retornoRegistro;
	}

}
