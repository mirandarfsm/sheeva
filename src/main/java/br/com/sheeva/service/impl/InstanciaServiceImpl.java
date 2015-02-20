package br.com.sheeva.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sheeva.dao.InstanciaDao;
import br.com.sheeva.dominio.Instancia;
import br.com.sheeva.service.InstanciaService;

@Service("instanciaService")
public class InstanciaServiceImpl implements InstanciaService {

	private static final String ENCODING = "UTF-8";

	@Autowired
	private InstanciaDao instanciaDao;

	public void salvar(Instancia instancia) {
		instanciaDao.save(instancia);
	}

	public void remover(Integer id) {
		instanciaDao.remove(id);
	}

	public List<Instancia> listarTodos() {
		return instanciaDao.getAll();
	}

	public Instancia buscarPeloId(Integer id) {
		return instanciaDao.getById(id);
	}

	public Instancia buscarPeloNome(String nome) {
		return instanciaDao.getByNome(nome);
	}

}
