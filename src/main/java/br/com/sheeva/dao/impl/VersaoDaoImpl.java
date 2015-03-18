package br.com.sheeva.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sheeva.dao.SistemaDao;
import br.com.sheeva.dao.VersaoDao;
import br.com.sheeva.dominio.Instancia;
import br.com.sheeva.dominio.Versao;

@Service("versaoDao")
@Transactional
public class VersaoDaoImpl implements VersaoDao {

	@PersistenceContext
	private EntityManager entityManager;

	public void save(Versao versao) {
		entityManager.merge(versao);
	}

	public void remove(Integer id) {
		Query query = entityManager.createNamedQuery("Versao.removeById")
				.setParameter("idVersao", id);
		query.executeUpdate();
	}

	public List<Versao> getAll() {
		Query query = entityManager.createNamedQuery("Versao.searchAll");
		List<Versao> versoes = query.getResultList();
		return versoes;
	}

	public Versao getById(Integer id) {
		Query query = entityManager.createNamedQuery("Versao.searchById")
				.setParameter("idVersao", id);
		Versao versao = (Versao) query.getSingleResult();
		return versao;
	}

	public Versao getByVersion(String versao) {
		List<Versao> versoes = entityManager
				.createNamedQuery("Versao.obterPelaVersao")
				.setParameter("versao", versao).getResultList();
		if (versoes == null || versoes.isEmpty()) {
			return null;
		}
		return versoes.get(0);
	}

	public List<Versao> getVersionList(Versao antiga, Versao nova) {
		Query query = entityManager.createNamedQuery("Versao.obterListaVersao")
				.setParameter("sistema", antiga.getSistema())
				.setParameter("versionAntiga", antiga.getVersion()).setParameter("versionNova", nova.getVersion())
				.setParameter("releaseAntiga", antiga.getRelease()).setParameter("releaseNova", nova.getRelease())
				.setParameter("sprintAntiga", antiga.getSprint()).setParameter("sprintNova", nova.getSprint())
				.setParameter("bugAntiga", antiga.getBug()).setParameter("bugNova", nova.getBug());
		List<Versao> versoes = query.getResultList();
		return versoes;
	}
	
	public List<Versao> getVersionBySystemOrder(Integer idSistema) {
		Query query = entityManager.createNamedQuery("Versao.obterListaVersaoPorSistemaOrdenado").setParameter("idSistema", idSistema);
		List<Versao> versoes = query.getResultList();
		return versoes;
	}

}
