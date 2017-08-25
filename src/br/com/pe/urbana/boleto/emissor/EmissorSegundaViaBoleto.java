package br.com.pe.urbana.boleto.emissor;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import br.com.caelum.stella.boleto.transformer.GeradorDeBoleto;
import br.com.pe.urbana.entidade.EntidadeCobranca;
import br.com.pe.urbana.entidade.EntidadeUsuario;
import net.sf.jasperreports.engine.util.JRLoader;

public class EmissorSegundaViaBoleto {
 
	public byte[] gerarBoleto(EntidadeUsuario usuario, EntidadeCobranca cobranca, HttpServletRequest request) throws Exception {
	
		byte[] pdfBytes = null;
		
		Boleto boleto = criarBoleto(usuario, cobranca);
		
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
						
		return pdfBytes;
	}
	
	private Boleto criarBoleto(EntidadeUsuario usuario, EntidadeCobranca cobranca) {
		
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
		
		Beneficiario beneficiario = criarBeneficiario(cobranca.getNossoNumeroFormatado());
		
		Date dt = cobranca.getDataVencimento();
		
		Calendar dataVencimento = Calendar.getInstance();
		dataVencimento.setTime(dt);
		
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
        		.comAceite(false) // BOLETO PROPOSTA
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
                .comValorBoleto(cobranca.getValor())
                .comNumeroDoDocumento(beneficiario.getNossoNumero())
                .comInstrucoes("SR. CAIXA, FAVOR RECEBER O BOLETO MESMO APÓS A DATA DE VENCIMENTO, SEM", "COBRANÇA DE MULTA E JUROS.", "PAGAMENTO APENAS EM DINHEIRO.")
                .comLocaisDePagamento("Até o vencimento, preferencialmente no Itaú / Após o vencimento, somente no Itaú");

		return boleto;
   	}

	private Beneficiario criarBeneficiario(String nossoNumero) {

		GeradorDeDigitoPadrao geradorDV = new GeradorDeDigitoPadrao();
		
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
                .comNossoNumero(nossoNumero);
                
        //GERANDO DV DO NOSSO NÚMERO
        String bloco = beneficiario.getAgencia()
        		.concat(beneficiario.getCodigoBeneficiario())
        		.concat(String.valueOf(beneficiario.getCarteira()))
        		.concat(beneficiario.getNossoNumero());
        int dv = geradorDV.geraDigitoBloco1(bloco);
        
        beneficiario.comDigitoNossoNumero(String.valueOf(dv));
        
		return beneficiario;
	}

}
