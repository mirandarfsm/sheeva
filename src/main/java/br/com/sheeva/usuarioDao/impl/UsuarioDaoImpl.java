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
		Usuario usuario = (Usuario) entityManager.createNamedQuery("Usuario.obterPeloLogin").setParameter("login", login).getSingleResult();
		return usuario;
	}
	
	public boolean verificarLoginEmUso(Usuario usuario) {
		if(usuario.getId() == null){
			Usuario usuarioExistente = getByLogin(usuario.getLogin());
			if (usuarioExistente != null && !usuarioExistente.equals(usuario)) {
				String message = "Já existe pessoa cadastrada com este login");
				throw new SigadaerException(message);
			}

		}
	}

}
