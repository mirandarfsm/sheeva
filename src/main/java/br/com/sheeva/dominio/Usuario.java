package br.com.sheeva.dominio;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.sheeva.enu.Perfil;

@NamedQueries({
	@NamedQuery(name="Usuario.removeById", query="UPDATE Usuario user SET user.excluido = true WHERE user.id = :idUsuario"),
	@NamedQuery(name="Usuario.searchById", query="SELECT user FROM Usuario user WHERE user.id = :idUsuario"),
	@NamedQuery(name="Usuario.searchAll", query="SELECT user FROM Usuario user WHERE user.excluido = false"),
	@NamedQuery(name="Usuario.obterPeloLogin", query="SELECT user FROM Usuario user WHERE user.login = :login " +
			"and user.excluido = false"),
	@NamedQuery(name="Usuario.obterPeloEmail", query="SELECT user FROM Usuario user WHERE user.email = :email " +
			"and user.excluido = false")
})
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable, UserDetails {

	private Integer id;
	private String nome;
	private boolean excluido;
	private Set<Perfil> perfis;
	private String login;
	private String senha;
	private String email;
	private static final long serialVersionUID = 1L;

	public Usuario() {
		super();
	}
	
	public Usuario(Integer id, String nome, Set<Perfil> perfis) {
		super();
		this.id = id;
		this.nome = nome;
		this.perfis = perfis;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario")
	@SequenceGenerator(name = "seq_usuario", sequenceName = "seq_usuario", allocationSize = 1, initialValue = 10000)
	@Column(unique = true, nullable = false, updatable = false, insertable = true)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@NotBlank(message="O campo NOME é obrigatório.")
	@Column(nullable = false)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(nullable = false)
	public boolean isExcluido() {
		return excluido;
	}

	public void setExcluido(boolean excluido) {
		this.excluido = excluido;
	}

	@ElementCollection(fetch=FetchType.EAGER)
	@Enumerated(EnumType.ORDINAL)
	public Set<Perfil> getPerfis() {
		if(perfis == null){
			perfis = new HashSet<Perfil>();
		}
		return perfis;
	}

	public void setPerfis(Set<Perfil> perfis) {
		this.perfis = perfis;
	}

	@NotBlank(message="O campo LOGIN é obrigatório.")
	@Column(nullable = false, unique = true)
	public String getLogin() {
		if (login != null) {
			return login.toLowerCase();
		}
		return login;
	}

	public void setLogin(String login) {
		if (login != null) {
			this.login = login.toLowerCase();
		}else {
			this.login = login;
		}
	}

	@NotBlank(message="O campo SENHA é obrigatório.")
	@Column(nullable = false)
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Email(message="E-mail inválido.")
	@NotBlank(message="O campo E-MAIL é obrigatório.")
	@Column(unique = true)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Transient
	public Collection<GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities= new HashSet<GrantedAuthority>();
		authorities.add(new GrantedAuthorityImpl("ROLE_USER"));
		for(Perfil perfil : perfis){
			authorities.add(new GrantedAuthorityImpl(perfil.toString()));
		}
		return authorities;
	}
	
	@Transient
	public boolean hasPerfil(Perfil perfil){
		return getPerfis().contains(perfil);
	}

	@Override
	public String toString() {
		return nome;
	}

	@Transient
	public String getPassword() {
		return senha;
	}

	@Transient
	public String getUsername() {
		return login;
	}

	@Transient
	public boolean isAccountNonExpired() {
		return true;
	}

	@Transient
	public boolean isAccountNonLocked() {
		return true;
	}

	@Transient
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Transient
	public boolean isEnabled() {
		return !excluido;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


}
