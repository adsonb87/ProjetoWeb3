package br.com.pe.urbana.entidade;

public class EntidadeNossoNumero {
	
	private Integer id;
	private Integer seqInicial;
	private Integer seqFinal;
	private Integer seqValor;
	
	public EntidadeNossoNumero() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSeqInicial() {
		return seqInicial;
	}

	public void setSeqInicial(Integer seqInicial) {
		this.seqInicial = seqInicial;
	}

	public Integer getSeqFinal() {
		return seqFinal;
	}

	public void setSeqFinal(Integer seqFinal) {
		this.seqFinal = seqFinal;
	}

	public Integer getSeqValor() {
		return seqValor;
	}

	public void setSeqValor(Integer seqValor) {
		this.seqValor = seqValor;
	}
	
	public String getSeqValorFormatado() {
		return String.format("%08d", seqValor);
	}

}
