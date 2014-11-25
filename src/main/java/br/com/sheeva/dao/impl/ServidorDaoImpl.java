package br.com.sheeva.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sheeva.dao.ServidorDao;
import br.com.sheeva.dominio.Servidor;

@Service("servidorDao")
@Transactional
public class ServidorDaoImpl implements ServidorDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public void save(Servidor servidor) {
		entityManager.merge(servidor);
	}

	public void remove(Integer id) {
		Query query = entityManager.createNamedQuery("Servidor.removeById").setParameter("idServidor", id);
		query.executeUpdate();
	}

	public List<Servidor> getAll() {
		Query query = entityManager.createNamedQuery("Servidor.searchAll");
		List<Servidor> servidores = query.getResultList();
		return servidores;
	}

	public Servidor getById(Integer id) {
		Query query = entityManager.createNamedQuery("Servidor.searchById").setParameter("idServidor", id);
		Servidor servidor = (Servidor) query.getSingleResult();
		return servidor;
	}
	
}
