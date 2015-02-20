package br.com.sheeva.dominio;

import java.io.InputStream;

public class Arquivo {

	private String nome;
	private InputStream arquivo;

	public Arquivo() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public InputStream getArquivo() {
		return arquivo;
	}

	public void setArquivo(InputStream arquivo) {
		this.arquivo = arquivo;
	}

}
