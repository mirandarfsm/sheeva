package br.com.sheeva.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "instancia")
public class Instancia {
	
	private Integer id;
	private String nome;
	private String diretorioPrincipal;
	private String arquivoConfiguração;

	public Instancia() {
		super();
	}
	
	public Instancia(Integer id, String nome, String diretorioPrincipal,
			String arquivoConfiguração) {
		super();
		this.id = id;
		this.nome = nome;
		this.diretorioPrincipal = diretorioPrincipal;
		this.arquivoConfiguração = arquivoConfiguração;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_instancia")
	@SequenceGenerator(name = "seq_instancia", sequenceName = "seq_instancia", allocationSize = 1, initialValue = 10000)
	@Column(unique = true, nullable = false, updatable = false, insertable = true)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDiretorioPrincipal() {
		return diretorioPrincipal;
	}

	public void setDiretorioPrincipal(String diretorioPrincipal) {
		this.diretorioPrincipal = diretorioPrincipal;
	}

	public String getArquivoConfiguração() {
		return arquivoConfiguração;
	}

	public void setArquivoConfiguração(String arquivoConfiguração) {
		this.arquivoConfiguração = arquivoConfiguração;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((arquivoConfiguração == null) ? 0 : arquivoConfiguração
						.hashCode());
		result = prime
				* result
				+ ((diretorioPrincipal == null) ? 0 : diretorioPrincipal
						.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Instancia other = (Instancia) obj;
		if (arquivoConfiguração == null) {
			if (other.arquivoConfiguração != null)
				return false;
		} else if (!arquivoConfiguração.equals(other.arquivoConfiguração))
			return false;
		if (diretorioPrincipal == null) {
			if (other.diretorioPrincipal != null)
				return false;
		} else if (!diretorioPrincipal.equals(other.diretorioPrincipal))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

}
