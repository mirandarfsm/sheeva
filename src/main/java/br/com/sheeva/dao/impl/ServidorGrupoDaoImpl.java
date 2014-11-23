package br.com.sheeva.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.sheeva.dao.ServidorGrupoDao;
import br.com.sheeva.dominio.Servidor;
import br.com.sheeva.dominio.ServidorGrupo;

public class ServidorGrupoDaoImpl implements ServidorGrupoDao {

	@PersistenceContext
	private EntityManager entityManager;
		
	public void save(ServidorGrupo servidorGrupo) {
		entityManager.merge(servidorGrupo);
	}

	public void remove(Integer id) {
		Query query = entityManager.createNamedQuery("ServidorGrupo.removeById").setParameter("idServidorGrupo", id);
		query.executeUpdate();
	}

	public List<ServidorGrupo> getAll() {
		Query query = entityManager.createNamedQuery("ServidorGrupo.searchAll");
		List<ServidorGrupo> servidorGrupo = query.getResultList();
		return servidorGrupo;
	}

	public ServidorGrupo getById(Integer id) {
		Query query = entityManager.createNamedQuery("ServidorGrupo.searchById").setParameter("idServidorGrupo", id);
		ServidorGrupo servidorGrupo = (ServidorGrupo) query.getSingleResult();
		return servidorGrupo;
	}

	public ServidorGrupo getByNome(String nome) {
		List<ServidorGrupo> servidorGrupo = entityManager.createNamedQuery("ServidorGrupo.obterPeloNome").setParameter("nomeGrupo", nome).getResultList();
		if (servidorGrupo == null || servidorGrupo.isEmpty()) {
			return null;
		}
		return servidorGrupo.get(0);
	}

}
