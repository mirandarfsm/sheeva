package br.com.sheeva.dominio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@NamedQueries({
		@NamedQuery(name = "Servidor.removeById", query = "DELETE FROM Servidor server WHERE server.id = :idServidor"),
		@NamedQuery(name = "Servidor.searchAll", query = "SELECT server FROM Servidor server"),
		@NamedQuery(name = "Servidor.searchById", query = "SELECT server FROM Servidor server WHERE server.id = :idServidor") })
@Entity
@Table(name = "servidor")
public class Servidor {

	private Integer id;
	private String nome;
	private String endereco;
	private Integer porta;
	private String login;
	private String senha;
	private Integer portaMonitoramento;
	private List<Instancia> instancias;

	public Servidor() {
		super();
		this.instancias = new ArrayList<Instancia>();
	}

	public Servidor(String nome, String endereco, Integer porta, String login,
			String senha) {
		this.nome = nome;
		this.endereco = endereco;
		this.porta = porta;
		this.login = login;
		this.senha = senha;
	}

	public Servidor(Integer id, String nome, String endereco, Integer porta,
			String login, String senha, List<Instancia> instancias) {
		this.id = id;
		this.nome = nome;
		this.endereco = endereco;
		this.porta = porta;
		this.login = login;
		this.senha = senha;
		this.instancias = instancias;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_servidor")
	@SequenceGenerator(name = "seq_servidor", sequenceName = "seq_servidor", allocationSize = 1, initialValue = 10000)
	@Column(unique = true, nullable = false, updatable = false, insertable = true)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@NotBlank(message = "O campo NOME é obrigatório.")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@NotBlank(message = "O campo ENDEREÇO é obrigatório.")
	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	@NotNull(message = "O campo PORTA é obrigatório.")
	public Integer getPorta() {
		return porta;
	}

	public void setPorta(Integer porta) {
		this.porta = porta;
	}

	@NotBlank(message = "O campo LOGIN é obrigatório.")
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@NotBlank(message = "O campo SENHA é obrigatório.")
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	public List<Instancia> getInstancias() {
		return instancias;
	}

	public void setInstancias(List<Instancia> instancias) {
		this.instancias = instancias;
	}

	@Column(name="porta_monitoramento")
	public Integer getPortaMonitoramento() {
		return portaMonitoramento;
	}

	public void setPortaMonitoramento(Integer portaMonitoramento) {
		this.portaMonitoramento = portaMonitoramento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((porta == null) ? 0 : porta.hashCode());
		result = prime * result + ((portaMonitoramento == null) ? 0 : portaMonitoramento.hashCode());
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
		Servidor other = (Servidor) obj;
		if (endereco == null) {
			if (other.endereco != null)
				return false;
		} else if (!endereco.equals(other.endereco))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (porta == null) {
			if (other.porta != null)
				return false;
		} else if (!porta.equals(other.porta))
			return false;
		if (portaMonitoramento == null) {
			if (other.portaMonitoramento != null)
				return false;
		} else if (!portaMonitoramento.equals(other.portaMonitoramento))
			return false;
		return true;
	}

}
