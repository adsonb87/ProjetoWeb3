package br.com.pe.urbana.boleto;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import br.com.caelum.stella.boleto.Endereco;
import br.com.caelum.stella.boleto.Pagador;

public class TesteEmissor {

	public static void main(String[] args) {
		
		EmissorBoleto emissorBoleto = new EmissorBoleto();
		
		Endereco enderecoPagador = Endereco.novoEndereco()
				.comLogradouro("RUA RIO BRANCO, 123")
        		.comBairro("JARDIM BRASIL")
        		.comCep("53290-270")
        		.comCidade("OLINDA")
        		.comUf("PE");
	        
		Pagador pagador = Pagador.novoPagador()
                .comNome("MARIA EDUARDA")
                .comDocumento("422.712.211-29")
                .comEndereco(enderecoPagador);
		
		byte[] pdfBytes = null;
//		try {
//			pdfBytes = emissorBoleto.gerarBoletoEmBytes(pagador);
//		} catch (FileNotFoundException | JRException e1) {
//			e1.printStackTrace();
//		}
		
		try {
			OutputStream out = new FileOutputStream(pagador.getNome() + ".pdf");
			out.write(pdfBytes);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		BoletoContoller ctrBoleto = BoletoContoller.getInstance();
//		UsuarioContoller ctrUsuario = UsuarioContoller.getInstance();
//		EmissorBoletoSegundaVia emissor = new EmissorBoletoSegundaVia();
//		EntidadeCobranca cobranca = null;
//		EntidadeUsuario usuario = null;
//		String cpf = "453.121.969-40";
//		
//		try {
//			cobranca = ctrBoleto.consultarBoleto(cpf);
//			usuario = ctrUsuario.consultarCpf(Util.unMaskCep(cpf));
//			if(usuario == null){
//				usuario = ctrUsuario.consultarCpfNovo(Util.unMaskCnpj(cpf));
//			}
//		} catch (ClassNotFoundException | NamingException | SQLException e) {
//			e.printStackTrace();
//		}
//		
//		Endereco endereco = Endereco.novoEndereco()
//				.comLogradouro(usuario.getEndereco().getLogradouro() + ", " + usuario.getEndereco().getNumero())
//				.comBairro(usuario.getEndereco().getBairro())
//				.comCidade(usuario.getEndereco().getCidade())
//				.comUf(usuario.getEndereco().getUf())
//				.comCep(usuario.getEndereco().getCep());
//		
//		Pagador pagador= Pagador.novoPagador()
//				.comNome(usuario.getNome())
//				.comDocumento(usuario.getCpf())
//				.comEndereco(endereco);
//			
//		byte[] boletoPDF = emissor.gerarBoletoEmBytes(pagador, cobranca);
		
	}

}
