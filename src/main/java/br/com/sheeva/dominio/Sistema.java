package br.com.sheeva.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@NamedQueries({
		@NamedQuery(name = "Sistema.removeById", query = "DELETE FROM Sistema system WHERE system.id = :idSistema"),
		@NamedQuery(name = "Sistema.searchAll", query = "SELECT system FROM Sistema system"),
		@NamedQuery(name = "Sistema.searchById", query = "SELECT system FROM Sistema system WHERE system.id = :idSistema"),
		@NamedQuery(name = "Sistema.obterPeloNome", query = "SELECT system FROM Sistema system WHERE system.nome = :nome") })
@Entity
@Table(name = "sistema")
public class Sistema {

	private Integer id;
	private String nome;
	private String arquivo;
	private String versao;

	public Sistema() {

	}

	public Sistema(Integer id, String nome, String arquivo, String versao) {
		this.id = id;
		this.nome = nome;
		this.arquivo = arquivo;
		this.versao = versao;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_sistema")
	@SequenceGenerator(name = "seq_sistema", sequenceName = "seq_sistema", allocationSize = 1, initialValue = 10000)
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

	public String getArquivo() {
		return arquivo;
	}

	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}

	public String getVersao() {
		return versao;
	}

	public void setVersao(String versao) {
		this.versao = versao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arquivo == null) ? 0 : arquivo.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Sistema other = (Sistema) obj;
		if (arquivo == null) {
			if (other.arquivo != null)
				return false;
		} else if (!arquivo.equals(other.arquivo))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (versao == null) {
			if (other.versao != null)
				return false;
		} else if (!versao.equals(other.versao))
			return false;
		return true;
	}

}
