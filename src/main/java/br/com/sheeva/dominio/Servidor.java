package br.com.sheeva.dominio;

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

import org.hibernate.validator.constraints.NotBlank;


@NamedQueries({
	@NamedQuery(name="Servidor.removeById", query="DELETE FROM Servidor server WHERE server.id = :idServidor"),
	@NamedQuery(name="Servidor.searchAll", query="SELECT server FROM Servidor server"),
	@NamedQuery(name="Servidor.searchById", query="SELECT server FROM Servidor server WHERE server.id = :idServidor")
})

@Entity
@Table(name = "servidor")
public class Servidor {

	private	Integer id;
	private String nome;
	private String endereco;
	private String login;
	private String senha;
	private List<Instancia> instancias;

	public Servidor(){
		super();
	}
	
	public Servidor(Integer id, String nome, String endereco, String login,
			String senha, List<Instancia> instancias) {
		super();
		this.id = id;
		this.nome = nome;
		this.endereco = endereco;
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
	

	@NotBlank(message="O campo NOME é obrigatório.")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@NotBlank(message="O campo ENDEREÇO é obrigatório.")
	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}


	@NotBlank(message="O campo LOGIN é obrigatório.")
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@NotBlank(message="O campo SENHA é obrigatório.")
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result
				+ ((instancias == null) ? 0 : instancias.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
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
		if (instancias == null) {
			if (other.instancias != null)
				return false;
		} else if (!instancias.equals(other.instancias))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		return true;
	}

}
