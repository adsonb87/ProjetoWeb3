package br.com.pe.urbana.entidade;

public class EntidadeCartao {
	
	private int id;
	
	private int usrId;
	
	private String viaCartao;

	private String motivoBloq;
	
	private String cpf;
	
	private int cdId;
	
	private int crdSnr;

	private int crdSnrAtual;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUsrId() {
		return usrId;
	}

	public void setUsrId(int usrId) {
		this.usrId = usrId;
	}

	public String getViaCartao() {
		return viaCartao;
	}

	public void setViaCartao(String viaCartao) {
		this.viaCartao = viaCartao;
	}

	public String getMotivoBloq() {
		return motivoBloq;
	}

	public void setMotivoBloq(String motivoBloq) {
		this.motivoBloq = motivoBloq;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public int getCdId() {
		return cdId;
	}

	public void setCdId(int cdId) {
		this.cdId = cdId;
	}

	public int getCrdSnr() {
		return crdSnr;
	}

	public void setCrdSnr(int crdSnr) {
		this.crdSnr = crdSnr;
	}

	public int getCrdSnrAtual() {
		return crdSnrAtual;
	}

	public void setCrdSnrAtual(int crdSnrAtual) {
		this.crdSnrAtual = crdSnrAtual;
	}
	
}
