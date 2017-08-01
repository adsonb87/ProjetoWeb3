package br.com.pe.urbana.boleto;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpServletRequest;

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
import br.com.pe.urbana.controller.BoletoContoller;
import br.com.pe.urbana.controller.UsuarioContoller;
import br.com.pe.urbana.entidade.EntidadeCobranca;
import br.com.pe.urbana.entidade.EntidadeNossoNumero;
import br.com.pe.urbana.entidade.EntidadeUsuario;
import br.com.pe.urbana.entidade.Status;
import net.sf.jasperreports.engine.util.JRLoader;

public class EmissorBoleto {
	
	@SuppressWarnings("deprecation")
	public byte[] gerarBoletoEmBytes(HttpServletRequest request, EntidadeUsuario usuario) throws Exception {
		
		// EXTRAINDO DADOS DO USU�RIO PARA O PAGADOR
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
		
		byte[] pdfBytes = null;
		Boleto boleto = criarBoleto(pagador);

		// CARREGA O CAMINHO F�SICO DOS ARQUIVOS
		String template = request.getServletContext().getRealPath("/WEB-INF/jasper/boleto-urbana.jasper");
		String template_sub = request.getServletContext().getRealPath("/WEB-INF/jasper/boleto-urbana_instrucoes.jasper");
		String logoUrbana = request.getServletContext().getRealPath("/WEB-INF/jasper/logoUrbana.png");
		String logoBanco = request.getServletContext().getRealPath("/WEB-INF/jasper/logoBanco.png");
		
		// MAPA PARA PAR�METROS
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("logo_urbana", logoUrbana);
		parametros.put("logo_banco", logoBanco);
		parametros.put("SUB_INSTRUCOES", JRLoader.loadObject(template_sub));
		
		// CARREGA O CONTE�DO DO ARQUIVO EM UM INPUTSTREAM
		InputStream templateBoleto = new FileInputStream(template);

		// PASSA PARA O GERADOR DE BOLETO O DADOS DO TEMPLATE NO CONSTRUTOR, 
		// JUNTO COM O MAPA DE PAR�METROS, AL�M DOS DADOS DOS BOLETOS
		GeradorDeBoleto gerador = new GeradorDeBoleto(templateBoleto, parametros, boleto);
		pdfBytes = gerador.geraPDF();
		
		if(pdfBytes != null) {
			UsuarioContoller ctrUsuario = UsuarioContoller.getInstance();
			BoletoContoller ctrBoleto = BoletoContoller.getInstance();
						
			EntidadeCobranca cobranca = new EntidadeCobranca();
			Status status = new Status();
			status.setId(4);
			
			cobranca.setCpfPagador(boleto.getPagador().getDocumento());
			cobranca.setNossoNumero(Integer.parseInt(boleto.getBeneficiario().getNossoNumero()));
			cobranca.setDataVencimento(boleto.getDatas().getVencimento().getTime());
			cobranca.setDataProcessamento(boleto.getDatas().getProcessamento().getTime());
			cobranca.setValor(boleto.getValorCobrado());
			cobranca.setStatus(status); //GERADO
			cobranca.setRegUser("SISTEMA");
			
			try {
				// SALVA O USU�RIO, A COBRA�A E ATUALIZA O NOSSO N�MERO
				ctrUsuario.cadastrarUsuarioNovo(usuario);
				ctrBoleto.salvarCobranca(cobranca);
				ctrBoleto.atualizarNossoNumero(Integer.parseInt(boleto.getBeneficiario().getNossoNumero()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// CRIAR O JSON DO BOLETO
		//criarJsonBoleto(boleto);
		
		return pdfBytes;
	}
	
	private Boleto criarBoleto(Pagador pagador) {
		
		Beneficiario beneficiario = criarBeneficiario();
		
		// DATA DE VENCIMENTO DO BOLETO
		Calendar dataVencimento = Calendar.getInstance();
		dataVencimento.add(Calendar.DAY_OF_MONTH, +5);
		
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
        		.comTipoAutorizacao("3"); // QUANDO O T�TULO N�O DEVE ACEITAR PAGAMENTO DE VALORES DIVERGENTES AO DA COBRAN�A

        InstrucaoCobranca instrucaoCobranca1 = InstrucaoCobranca.novaInstrucaoCobranca()
        		.comInstrucao("90"); // NO VENCIMENTO, PAG�VEL EM QUALQUER AG�NCIA BANC�RIA
        
        Banco banco = new Itau(); 

        Boleto boleto = Boleto.novoBoleto()
        		.comEspecieMoeda("R$")
        		.comCodigoEspecieMoeda(9)
        		.comTipoAmbiente(1) // PARA TESTE
        		.comTipoRegistro(1) // PARA REGISTRO
        		.comTipoCobranca(1) // PARA BOLETOS
        		.comTipoProduto("00006")
        		.comSubProduto("00008")
        		.comAceite(false) // BOLETO PROPOSTA
        		.comEspecie(01) // DUPLICATA MERCANTIL
        		.comTipoPagamento(1) // PAGAMENTO REALIZADO � VISTA
        		.comIndicadorPagamentoParcial(false) // N�O ACEITA PAGAMENTO PARCIAL
        		.comJuros(juros) 
        		.comMulta(multa)
        		.comDesconto(desconto)
        		.comRecebimentoDivergente(recebimentoDivergente)
        		.comInstrucaoCobranca1(instrucaoCobranca1)
                .comBanco(banco)
                .comDatas(datas)
                .comBeneficiario(beneficiario)
                .comPagador(pagador)
                .comValorBoleto("20.00")
                .comNumeroDoDocumento(beneficiario.getNossoNumero())
                .comEspecieDocumento("RC")
                .comInstrucoes("SR. CAIXA, FAVOR RECEBER O BOLETO MESMO AP�S A DATA DE VENCIMENTO, SEM", "COBRAN�A DE MULTA E JUROS.", "PAGAMENTO APENAS EM DINHEIRO.")
                .comLocaisDePagamento("At� o vencimento, preferencialmente no Ita�");
    
		return boleto;
   	}

	private Beneficiario criarBeneficiario() {

		// RESPONS�VEL PELA GERA��O DO DIGITO VERIFICADOR
		GeradorDeDigitoPadrao geradorDV = new GeradorDeDigitoPadrao();

		// PEGA O VALOR ATUAL DO NOSSO N�MERO NO BANCO 
		EntidadeNossoNumero nossoNumero = getNossoNumero();
		
		Endereco enderecoBeneficiario = Endereco.novoEndereco()
        		.comLogradouro("R. DA SOLEDADE")
        		.comBairro("BOA VISTA")  
        		.comCep("50070-040")  
        		.comCidade("RECIFE")
        		.comUf("PE");

        Beneficiario beneficiario = Beneficiario.novoBeneficiario()
                .comNomeBeneficiario("SIND DAS EMP DE TRANSP DE PASSAG DO EST DE PERNAM")
                .comDocumento("09.759.606/0001-80")
                .comAgencia("2938")
                .comDigitoAgencia("4")
                .comCodigoBeneficiario("24794")  
                .comDigitoCodigoBeneficiario("3")
                .comCarteira(109)
                .comEndereco(enderecoBeneficiario)
                .comNossoNumero(String.valueOf(nossoNumero.getSeqValor()));
                
        //GERANDO DV DO NOSSO N�MERO
        String bloco = beneficiario.getAgencia()
        		.concat(beneficiario.getCodigoBeneficiario())
        		.concat(String.valueOf(beneficiario.getCarteira()))
        		.concat(beneficiario.getNossoNumero());
        int dv = geradorDV.geraDigitoBloco1(bloco);
        
        beneficiario.comDigitoNossoNumero(String.valueOf(dv));
        
		return beneficiario;
	}
	
	// RESPONS�VEL POR PEGAR O NOSSO N�MERO
	private EntidadeNossoNumero getNossoNumero() {
		
		BoletoContoller ctr = BoletoContoller.getInstance();
		EntidadeNossoNumero nossoNumero = null;
		
		try {
			nossoNumero = ctr.getNossoNumero();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return nossoNumero;
	}
	
	// CRIA O JSON DO BOLETO
	private void criarJsonBoleto(Boleto boleto) {
		
		// GERA O JSON E RETORNA UM JSONOBJECTBUILDER
		JsonObjectBuilder boletoJson = GeradorJson.gerarJson(boleto);
 		
		try {
			FileWriter writer = new FileWriter("C:/projetos/vemcomum/" + boleto.getPagador().getNome() + ".json");
			writer.write(boletoJson.build().toString());
			writer.close();
	 
		} catch (IOException e) {
			e.printStackTrace();
		}
        		
	}

}
