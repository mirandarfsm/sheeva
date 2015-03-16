package br.com.sheeva.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sheeva.dao.SistemaDao;
import br.com.sheeva.dominio.Sistema;

@Service("sistemaDao")
@Transactional
public class SistemaDaoImpl implements SistemaDao {

	@PersistenceContext
	private EntityManager entityManager;

	public void save(Sistema sistema) {
		entityManager.merge(sistema);
	}

	public void remove(Integer id) {
		Query query = entityManager.createNamedQuery("Sistema.removeById")
				.setParameter("idSistema", id);
		query.executeUpdate();
	}

	public List<Sistema> getAll() {
		Query query = entityManager.createNamedQuery("Sistema.searchAll");
		List<Sistema> sistemas = query.getResultList();
		return sistemas;
	}

	public Sistema getById(Integer id) {
		Query query = entityManager.createNamedQuery("Sistema.searchById")
				.setParameter("idSistema", id);
		Sistema sistema = (Sistema) query.getSingleResult();
		return sistema;
	}

	public Sistema getByNome(String nome) {
		List<Sistema> sistemas = entityManager
				.createNamedQuery("Sistema.obterPeloNome")
				.setParameter("nome", nome).getResultList();
		if (sistemas == null || sistemas.isEmpty()) {
			return null;
		}
		return sistemas.get(0);
	}
	
	public int getIdSystemByInstance(Integer idInstancia) {
		Query query = entityManager.createNamedQuery("Sistema.obterSistemaPelaInstancia").setParameter("idInstancia", idInstancia);
		Sistema sistema = (Sistema) query.getSingleResult();
		return sistema.getId();
	}

}
