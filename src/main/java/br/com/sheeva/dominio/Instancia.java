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

import br.com.sheeva.enu.TipoInstancia;

@NamedQueries({
		@NamedQuery(name = "Instancia.removeById", query = "DELETE FROM Instancia instance WHERE instance.id = :idInstancia"),
		@NamedQuery(name = "Instancia.searchAll", query = "SELECT instance FROM Instancia instance"),
		@NamedQuery(name = "Instancia.searchById", query = "SELECT instance FROM Instancia instance WHERE instance.id = :idInstancia"),
		@NamedQuery(name = "Instancia.obterPeloNome", query = "SELECT instance FROM Instancia instance WHERE instance.nome = :nome") })
@Entity
@Table(name = "instancia")
public class Instancia {

	private Integer id;
	private String nome;
	private TipoInstancia tipoInstancia;
	private String diretorioPrincipal;
	private String arquivoConfiguracao;

	public Instancia() {
		super();
	}

	public Instancia(Integer id, String nome, String diretorioPrincipal,
			String arquivoConfiguracao) {
		super();
		this.id = id;
		this.nome = nome;
		this.diretorioPrincipal = diretorioPrincipal;
		this.arquivoConfiguracao = arquivoConfiguracao;
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

	@Column(name = "tipo_instancia")
	public TipoInstancia getTipoInstancia() {
		return tipoInstancia;
	}

	public void setTipoInstancia(TipoInstancia tipoInstancia) {
		this.tipoInstancia = tipoInstancia;
	}

	@Column(name = "diretorio_principal")
	public String getDiretorioPrincipal() {
		return diretorioPrincipal;
	}

	public void setDiretorioPrincipal(String diretorioPrincipal) {
		this.diretorioPrincipal = diretorioPrincipal;
	}

	@Column(name = "arquivo_configuracao")
	public String getArquivoConfiguracao() {
		return arquivoConfiguracao;
	}

	public void setArquivoConfiguracao(String arquivoConfiguracao) {
		this.arquivoConfiguracao = arquivoConfiguracao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((arquivoConfiguracao == null) ? 0 : arquivoConfiguracao
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
		if (arquivoConfiguracao == null) {
			if (other.arquivoConfiguracao != null)
				return false;
		} else if (!arquivoConfiguracao.equals(other.arquivoConfiguracao))
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
