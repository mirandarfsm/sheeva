package br.com.sheeva.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sheeva.dao.ServidorDao;
import br.com.sheeva.dominio.Servidor;
import br.com.sheeva.service.ServidorService;

@Service("servidorService")
public class ServidorServiceImpl implements ServidorService {
	
	@Autowired
	private ServidorDao servidorDao;

	public void salvar(Servidor servidor) {
		servidorDao.save(servidor);
	}

	public void remover(Integer id) {
		servidorDao.remove(id);
	}

	public List<Servidor> listarTodos() {
		return servidorDao.getAll();
	}

	public Servidor buscarPeloId(Integer id) {
		return servidorDao.getById(id);
	}

}
