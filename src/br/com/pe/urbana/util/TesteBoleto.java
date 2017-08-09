package br.com.pe.urbana.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import javax.json.JsonObjectBuilder;

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

public class TesteBoleto {

	public static void main(String[] args) {
		
		GeradorDeDigitoPadrao geradorDV = new GeradorDeDigitoPadrao();
		
        Endereco enderecoPagador = Endereco.novoEndereco()
        		.comLogradouro("Rua Dom José Pereira Alves, 171")
        		.comBairro("Cordeiro")
        		.comCep("50721-020")
        		.comCidade("Recife")
        		.comUf("PE");
        
        Pagador pagador = Pagador.novoPagador()
                .comNome("André Carlos Batista da Silva") 
                .comDocumento("701.806.866-58")
                .comEndereco(enderecoPagador);

        Endereco enderecoBeneficiario = Endereco.novoEndereco()
        		.comLogradouro("R. da Soledade") 
        		.comBairro("Boa Vista")  
        		.comCep("50070-040")  
        		.comCidade("Recife")
        		.comUf("PE");  

        Beneficiario beneficiario = Beneficiario.novoBeneficiario()  
                .comNomeBeneficiario("Urbana-PE Empresas de Transporte Integrado")
                .comDocumento("09.759.606/0002-60")
                .comAgencia("9988")
                .comDigitoAgencia("6") 
                .comCodigoBeneficiario("54300")  
                .comDigitoCodigoBeneficiario("1")
                .comNumeroConvenio("1411398")  
                .comCarteira(109)
                .comEndereco(enderecoBeneficiario)
                .comNossoNumero("21000987");
        
        String aux = beneficiario.getAgencia() + beneficiario.getCodigoBeneficiario() + beneficiario.getCarteira() + beneficiario.getNossoNumero();
        int dv = geradorDV.geraDigitoBloco1(aux);
        
        beneficiario.comDigitoNossoNumero(String.valueOf(dv));

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
                .comValorBoleto("20.00")
                .comNumeroDoDocumento(beneficiario.getNossoNumero())
                .comEspecieDocumento("RC")
                .comInstrucoes("SR. CAIXA, FAVOR RECEBER O BOLETO MESMO APÓS A DATA DE VENCIMENTO, SEM", "COBRANÇA DE MULTA E JUROS.", "PAGAMENTO APENAS EM DINHEIRO.")
                .comLocaisDePagamento("Até o vencimento, preferencialmente no Itaú");
		
		GeradorDeBoleto gerador = new GeradorDeBoleto(boleto);
        gerador.geraPDF(pagador.getNome() + ".pdf");
		System.out.println("Boleto Gerado!");
	
		JsonObjectBuilder boletoJson = GeradorJson.gerarJson(boleto);
 		
		try {
			FileWriter writer = new FileWriter("boletoItau.json");
			writer.write(boletoJson.build().toString());
			writer.close();
	 
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		System.out.println("Json Gerado!");
		
	}	
}
