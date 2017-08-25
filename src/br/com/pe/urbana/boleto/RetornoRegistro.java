package br.com.pe.urbana.boleto;

import java.math.BigDecimal;
import java.util.List;

public class RetornoRegistro {
	
	private Integer status;
	private String codigo;
	private String mensagem;
	private List<Campos> campos;
	private Beneficiario beneficiario;
	private Pagador pagador;
	private Moeda moeda;
	private String especie_documento;
	private String vencimento_titulo;
	private String tipo_carteira_titulo;
	private String nosso_numero;
	private String seu_numero;
	private String codigo_barras;
	private String numero_linha_digitavel;
	private String local_pagamento;
	private String data_processamento;
	private String data_emissao;
	private String uso_banco;
	private BigDecimal valor_titulo;
	private BigDecimal valor_desconto;
	private BigDecimal valor_outra_deducao;
	private BigDecimal valor_juro_multa;
	private BigDecimal valor_outro_acrescimo;
	private BigDecimal valor_total_cobrado;
	private List<Informacoes> lista_texto_informacao_cliente_beneficiario;

	public RetornoRegistro(){}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public List<Campos> getCampos() {
		return campos;
	}

	public void setCampos(List<Campos> campos) {
		this.campos = campos;
	}

	public Beneficiario getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(Beneficiario beneficiario) {
		this.beneficiario = beneficiario;
	}

	public Pagador getPagador() {
		return pagador;
	}

	public void setPagador(Pagador pagador) {
		this.pagador = pagador;
	}

	public Moeda getMoeda() {
		return moeda;
	}

	public void setMoeda(Moeda moeda) {
		this.moeda = moeda;
	}

	public String getEspecie_documento() {
		return especie_documento;
	}

	public void setEspecie_documento(String especie_documento) {
		this.especie_documento = especie_documento;
	}

	public String getVencimento_titulo() {
		return vencimento_titulo;
	}

	public void setVencimento_titulo(String vencimento_titulo) {
		this.vencimento_titulo = vencimento_titulo;
	}

	public String getTipo_carteira_titulo() {
		return tipo_carteira_titulo;
	}

	public void setTipo_carteira_titulo(String tipo_carteira_titulo) {
		this.tipo_carteira_titulo = tipo_carteira_titulo;
	}

	public String getNosso_numero() {
		return nosso_numero;
	}

	public void setNosso_numero(String nosso_numero) {
		this.nosso_numero = nosso_numero;
	}

	public String getSeu_numero() {
		return seu_numero;
	}

	public void setSeu_numero(String seu_numero) {
		this.seu_numero = seu_numero;
	}

	public String getCodigo_barras() {
		return codigo_barras;
	}

	public void setCodigo_barras(String codigo_barras) {
		this.codigo_barras = codigo_barras;
	}

	public String getNumero_linha_digitavel() {
		return numero_linha_digitavel;
	}

	public void setNumero_linha_digitavel(String numero_linha_digitavel) {
		this.numero_linha_digitavel = numero_linha_digitavel;
	}

	public String getLocal_pagamento() {
		return local_pagamento;
	}

	public void setLocal_pagamento(String local_pagamento) {
		this.local_pagamento = local_pagamento;
	}

	public String getData_processamento() {
		return data_processamento;
	}

	public void setData_processamento(String data_processamento) {
		this.data_processamento = data_processamento;
	}

	public String getData_emissao() {
		return data_emissao;
	}

	public void setData_emissao(String data_emissao) {
		this.data_emissao = data_emissao;
	}

	public String getUso_banco() {
		return uso_banco;
	}

	public void setUso_banco(String uso_banco) {
		this.uso_banco = uso_banco;
	}

	public BigDecimal getValor_titulo() {
		return valor_titulo;
	}

	public void setValor_titulo(BigDecimal valor_titulo) {
		this.valor_titulo = valor_titulo;
	}

	public BigDecimal getValor_desconto() {
		return valor_desconto;
	}

	public void setValor_desconto(BigDecimal valor_desconto) {
		this.valor_desconto = valor_desconto;
	}

	public BigDecimal getValor_outra_deducao() {
		return valor_outra_deducao;
	}

	public void setValor_outra_deducao(BigDecimal valor_outra_deducao) {
		this.valor_outra_deducao = valor_outra_deducao;
	}

	public BigDecimal getValor_juro_multa() {
		return valor_juro_multa;
	}

	public void setValor_juro_multa(BigDecimal valor_juro_multa) {
		this.valor_juro_multa = valor_juro_multa;
	}

	public BigDecimal getValor_outro_acrescimo() {
		return valor_outro_acrescimo;
	}

	public void setValor_outro_acrescimo(BigDecimal valor_outro_acrescimo) {
		this.valor_outro_acrescimo = valor_outro_acrescimo;
	}

	public BigDecimal getValor_total_cobrado() {
		return valor_total_cobrado;
	}

	public void setValor_total_cobrado(BigDecimal valor_total_cobrado) {
		this.valor_total_cobrado = valor_total_cobrado;
	}
	
	public List<Informacoes> getLista_texto() {
		return lista_texto_informacao_cliente_beneficiario;
	}
	
	public void setLista_texto(List<Informacoes> lista_texto) {
		this.lista_texto_informacao_cliente_beneficiario = lista_texto;
	}
	
	@Override
	public String toString() {
		return "{\n cogido: " + codigo + "\n mensagem: " + mensagem + "\n campos: " + campos + "\n\n}";
	}

}