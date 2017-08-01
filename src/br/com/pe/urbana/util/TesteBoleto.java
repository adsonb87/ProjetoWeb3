package br.com.pe.urbana.util;

import java.io.FileWriter;
import java.io.IOException;

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
		
		Datas datas = Datas.novasDatas()
                .comDocumento(11, 7, 2017)
                .comProcessamento(11, 7, 2017)
                .comVencimento(15, 7, 2017);  

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
        
        Desconto desconto = Desconto.novoDesconto()
        		.comTipo(0);
        
        RecebimentoDivergente recebimentoDivergente = RecebimentoDivergente.novoRecebimentoDivergente()
        		.comTipoAutorizacao("1");
        
        Juros juros = Juros.novoJuros()
        		.comTipo(5);
        
        Multa multa = Multa.novaMulta()
        		.comTipo(3);
        
        InstrucaoCobranca instrucaoCobranca1 = InstrucaoCobranca.novaInstrucaoCobranca()
        		.comInstrucao("90");
        
        Banco banco = new Itau();  

        Boleto boleto = Boleto.novoBoleto()
        		.comTipoAmbiente(1)
        		.comTipoRegistro(1)
        		.comTipoCobranca(1)
        		.comTipoProduto("00006")
        		.comSubProduto("00008")
        		.comAceite(false)//boleto de proposta
        		.comEspecie(01)
        		.comTipoPagamento(1)
        		.comIndicadorPagamentoParcial(false)
        		.comJuros(juros)
        		.comMulta(multa)
        		.comDesconto(desconto)
        		.comRecebimentoDivergente(recebimentoDivergente)
        		.comInstrucaoCobranca1(instrucaoCobranca1)
                .comBanco(banco)
                .comDatas(datas)
                .comBeneficiario(beneficiario)
                .comPagador(pagador)
                .comValorBoleto("9.99")
                .comNumeroDoDocumento("9876")
                .comEspecieDocumento("RC")
                .comInstrucoes("No vencimento, pagável em qualquer agência bancária")//InstrucaoCobranca 90
                .comLocaisDePagamento("Preferencialmente no Banco Itaú");
		
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
		
		System.out.println("Cód Barras" + boleto.getCodigoDeBarras());
		System.out.println("Linha dig" + boleto.getLinhaDigitavel());
		
	}	
}
