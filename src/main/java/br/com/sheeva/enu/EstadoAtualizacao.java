package br.com.sheeva.enu;

public enum EstadoAtualizacao {
	NAO_INICIADA ("Não Iniciada"),
	EM_ANDAMENTO ("Em Andamento"),
	TERMINADA ("Terminada"),
	SEM_SUCESSO ("Sem Sucesso");
	
	String nome;
	
	private EstadoAtualizacao(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
	
}
