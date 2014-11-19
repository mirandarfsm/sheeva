package br.com.sheeva.enu;

public enum Perfil {
	PERFIL_CONVENCIONAL ("Convencional"),
	PERFIL_ADMINISTRADOR ("Administrador");
	
	String nome;
	
	private Perfil(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
	
}
