package br.com.sheeva.enu;

public enum TipoInstancia {
	
	SIGADAER("Sigadaer"),
	ONIX("Onix"),
	SISAUC("Sisauc");
	
	private String nome;

	private TipoInstancia(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

}
