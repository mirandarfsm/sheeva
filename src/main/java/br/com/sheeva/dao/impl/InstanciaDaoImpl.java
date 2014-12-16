package br.com.sheeva.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sheeva.dao.InstanciaDao;
import br.com.sheeva.dominio.Instancia;

@Service("instanciaDao")
@Transactional
public class InstanciaDaoImpl implements InstanciaDao {

	@PersistenceContext
	private EntityManager entityManager;

	public void save(Instancia instancia) {
		entityManager.merge(instancia);
	}

	public void remove(Integer id) {
		Query query = entityManager.createNamedQuery("Instancia.removeById")
				.setParameter("idInstancia", id);
		query.executeUpdate();
	}

	public List<Instancia> getAll() {
		Query query = entityManager.createNamedQuery("Instancia.searchAll");
		List<Instancia> instancias = query.getResultList();
		return instancias;
	}

	public Instancia getById(Integer id) {
		Query query = entityManager.createNamedQuery("Instancia.searchById")
				.setParameter("idInstancia", id);
		Instancia instancia = (Instancia) query.getSingleResult();
		return instancia;
	}

	public Instancia getByNome(String nome) {
		List<Instancia> instancias = entityManager
				.createNamedQuery("Instancia.obterPeloNome")
				.setParameter("nome", nome).getResultList();
		if (instancias == null || instancias.isEmpty()) {
			return null;
		}
		return instancias.get(0);
	}

}
