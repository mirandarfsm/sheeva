package br.com.sheeva.dominio;

import java.util.List;


public class ServidorGrupo {
	
	private String nome;
	private List servidores;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List getServidores() {
		return servidores;
	}

	public void setServidores(List servidores) {
		this.servidores = servidores;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result
				+ ((servidores == null) ? 0 : servidores.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServidorGrupo other = (ServidorGrupo) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (servidores == null) {
			if (other.servidores != null)
				return false;
		} else if (!servidores.equals(other.servidores))
			return false;
		return true;
	}

}
