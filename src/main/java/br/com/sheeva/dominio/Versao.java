package br.com.sheeva.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@NamedQueries({
		@NamedQuery(name = "Versao.removeById", query = "DELETE FROM Versao version WHERE version.id = :idVersao"),
		@NamedQuery(name = "Versao.searchAll", query = "SELECT version FROM Versao version"),
		@NamedQuery(name = "Versao.searchById", query = "SELECT version FROM Versao version WHERE version.id = :idVersao"),
		@NamedQuery(name = "Versao.obterPelaVersao", query = "SELECT version FROM Versao version WHERE version.versao = :versao"),
		@NamedQuery(name = "Versao.obterListaVersaoPorSistema", query = "SELECT version FROM Versao version WHERE version.sistema.id = :idSistema"),
		@NamedQuery(name = "Versao.obterListaVersao", query = "SELECT version FROM Versao version WHERE version.id between :idAntiga and :idNova") })
@Entity
@Table(name = "versao")
public class Versao {

	private Integer id;
	private Sistema sistema;
	private String versao;

	public Versao() {
	}

	public Versao(Integer id, Sistema sistema, String versao) {
		this.id = id;
		this.sistema = sistema;
		this.versao = versao;
	}
	
	public Versao(Sistema sistema, String versao) {
		this.sistema = sistema;
		this.versao = versao;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_versao")
	@SequenceGenerator(name = "seq_versao", sequenceName = "seq_versao", allocationSize = 1, initialValue = 10000)
	@Column(unique = true, nullable = false, updatable = false, insertable = true)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "sistema")
	public Sistema getSistema() {
		return sistema;
	}

	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}

	@Column(name = "versao")
	public String getVersao() {
		return versao;
	}

	public void setVersao(String versao) {
		this.versao = versao;
	}

	@Transient
	public String getFolder() {
		return sistema.getFolder() + this.versao + "/";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((sistema == null) ? 0 : sistema.hashCode());
		result = prime * result + ((versao == null) ? 0 : versao.hashCode());
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
		Versao other = (Versao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (sistema == null) {
			if (other.sistema != null)
				return false;
		} else if (!sistema.equals(other.sistema))
			return false;
		if (versao == null) {
			if (other.versao != null)
				return false;
		} else if (!versao.equals(other.versao))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.sistema + " - " + this.versao;
	}

}
