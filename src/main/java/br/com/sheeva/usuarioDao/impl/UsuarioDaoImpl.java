package br.com.sheeva.usuarioDao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sheeva.dominio.Usuario;
import br.com.sheeva.usuarioDao.UsuarioDao;

@Service("usuarioDao")
@Transactional
public class UsuarioDaoImpl implements UsuarioDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	public void save(Usuario usuario) {
		entityManager.merge(usuario);
	}

	public void remove(Integer id) {
		Query query = entityManager.createNamedQuery("Usuario.removeById").setParameter("idUsuario", id);
		query.executeUpdate();
	}

	public List<Usuario> getAll() {
		Query query = entityManager.createNamedQuery("Usuario.searchAll");
		List<Usuario> usuarios = query.getResultList();
		return usuarios;
	}

	public Usuario getById(Integer id) {
		Query query = entityManager.createNamedQuery("Usuario.searchById").setParameter("idUsuario", id);
		Usuario usuario = (Usuario) query.getSingleResult();
		return usuario;
	}

	public Usuario getByLogin(String login) {
		List<Usuario> usuarios = entityManager.createNamedQuery("Usuario.obterPeloLogin").setParameter("login", login).getResultList();
		if (usuarios == null || usuarios.isEmpty()) {
			return null;
		}
		return usuarios.get(0);
	}
	
	public Usuario getByEmail(String email) {
		List<Usuario> usuarios = entityManager.createNamedQuery("Usuario.obterPeloEmail").setParameter("email", email).getResultList();
		if (usuarios == null || usuarios.isEmpty()) {
			return null;
		}
		return usuarios.get(0);
	}

}
