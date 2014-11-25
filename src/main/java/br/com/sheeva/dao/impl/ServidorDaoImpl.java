package br.com.sheeva.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.sheeva.dao.ServidorDao;
import br.com.sheeva.dominio.Servidor;

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
	
	public Servidor getByNome(String nome) {
		List<Servidor> servidores = entityManager.createNamedQuery("Servidor.obterPeloNome").setParameter("nome", nome).getResultList();
		if (servidores == null || servidores.isEmpty()) {
			return null;
		}
		return servidores.get(0);
	}
	
	public Servidor getByInstanceName(String instanceName) {
		List<Servidor> servidores = entityManager.createNamedQuery("Servidor.obterPeloNomeInstancia").setParameter("nomeInstancia", instanceName).getResultList();
		if (servidores == null || servidores.isEmpty()) {
			return null;
		}
		return servidores.get(0);
	}

}
