package br.com.pe.urbana.boleto;

import java.util.Calendar;
import java.util.Date;

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

public class EmissorSegundaViaBoleto {
 
	public byte[] gerarBoletoEmBytes(EntidadeUsuario usuario, EntidadeCobranca cobranca) throws Exception {
		
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
		
		byte[] pdfBytes = null;
		
		Boleto boleto = criarBoleto(pagador, cobranca);
		
		GeradorDeBoleto gerador = new GeradorDeBoleto(boleto);
		pdfBytes = gerador.geraPDF();
				
		return pdfBytes;
	}
	
	private Boleto criarBoleto(Pagador pagador, EntidadeCobranca cobranca) {
		
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
        		.comEspecie("01") // DUPLICATA MERCANTIL
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
                .comValorBoleto("1.00")
                .comNumeroDoDocumento(beneficiario.getNossoNumero())
                .comEspecieDocumento("RC") // RECIBO
                .comInstrucoes("SR. CAIXA, FAVOR RECEBER O BOLETO MESMO APÓS A DATA DE VENCIMENTO, SEM", "COBRANÇA DE MULTA E JUROS.", "PAGAMENTO APENAS EM DINHEIRO.")
                .comLocaisDePagamento("Até o vencimento, preferencialmente no Itaú");

		return boleto;
   	}

	private Beneficiario criarBeneficiario(String nossoNumero) {

		GeradorDeDigitoPadrao geradorDV = new GeradorDeDigitoPadrao();
		
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
