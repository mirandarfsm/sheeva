package br.com.sheeva.dominio;

import javax.faces.bean.NoneScoped;
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
		@NamedQuery(name = "Versao.obterListaVersao", query = "SELECT version FROM Versao version WHERE version.versao between :antiga and :nova") })
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
		return sistema.getFolder() + "/" + this.versao;
	}

}
