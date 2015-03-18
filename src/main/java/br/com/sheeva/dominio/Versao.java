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
		@NamedQuery(name = "Versao.obterListaVersaoPorSistemaOrdenado", query = "SELECT version FROM Versao version "
				+ "WHERE version.sistema.id = :idSistema order by version.version, version.release, version.sprint, version.bug"),
		@NamedQuery(name = "Versao.obterListaVersao", query = "SELECT version FROM Versao version WHERE (version.sistema = :sistema) and (version.version> :versaoAntiga and version.version <= :versaoNova) and (version.release > :releaseAntiga and version.release <= :releaseNova) and (version.sprint > :sprintAntiga and version.sprint <= :sprintNova) and (version.bug > :bugAntiga and version.bug <= :bugNova) ") })
@Entity
@Table(name = "versao")
public class Versao {

	private Integer id;
	private Sistema sistema;
	private String arquivoAplicacao;
	private String arquivoBancoDados;
	private Integer version;
	private Integer release;
	private Integer sprint;
	private Integer bug;

	

	public Versao() {
		this.version = 0;
		this.release = 0;
		this.sprint = 0;
		this.bug = 0;
	}

	public Versao(Integer id, Sistema sistema, String versao) {
		this.id = id;
		this.sistema = sistema;
		this.setVersaoString(versao);
	}

	public Versao(Sistema sistema, String versao) {
		this.sistema = sistema;
		this.setVersaoString(versao);
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

	@Transient
	public String getFolder() {
		return sistema.getFolder() + this.getVersaoString() + "/";
	}

	@Column(name = "arquivo_aplicacao")
	public String getArquivoAplicacao() {
		return arquivoAplicacao;
	}

	public void setArquivoAplicacao(String arquivoAplicacao) {
		this.arquivoAplicacao = arquivoAplicacao;
	}

	@Column(name = "arquivo_banco_dados")
	public String getArquivoBancoDados() {
		return arquivoBancoDados;
	}

	public void setArquivoBancoDados(String arquivoBancoDados) {
		this.arquivoBancoDados = arquivoBancoDados;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getRelease() {
		return release;
	}

	public void setRelease(Integer release) {
		this.release = release;
	}

	public Integer getSprint() {
		return sprint;
	}

	public void setSprint(Integer sprint) {
		this.sprint = sprint;
	}

	public Integer getBug() {
		return bug;
	}

	public void setBug(Integer bug) {
		this.bug = bug;
	}

	@Transient
	public String getVersaoString() {
		return version + "." + release + "." + sprint + "." + bug;
	}

	public void setVersaoString(String versaoString) {
		String strings[] = versaoString.split("\\.");
		this.version = Integer.valueOf(strings[0]);
		this.release = Integer.valueOf(strings[1]);
		this.sprint = Integer.valueOf(strings[2]);
		this.bug = Integer.valueOf(strings[3]);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((arquivoAplicacao == null) ? 0 : arquivoAplicacao.hashCode());
		result = prime
				* result
				+ ((arquivoBancoDados == null) ? 0 : arquivoBancoDados
						.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (arquivoAplicacao == null) {
			if (other.arquivoAplicacao != null)
				return false;
		} else if (!arquivoAplicacao.equals(other.arquivoAplicacao))
			return false;
		if (arquivoBancoDados == null) {
			if (other.arquivoBancoDados != null)
				return false;
		} else if (!arquivoBancoDados.equals(other.arquivoBancoDados))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.sistema + " - " + this.getVersaoString();
	}

}
