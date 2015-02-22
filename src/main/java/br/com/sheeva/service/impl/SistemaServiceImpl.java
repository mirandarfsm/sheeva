package br.com.sheeva.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sheeva.dao.SistemaDao;
import br.com.sheeva.dominio.Arquivo;
import br.com.sheeva.dominio.Sistema;
import br.com.sheeva.dominio.Versao;
import br.com.sheeva.service.SistemaService;

@Service("sistemaService")
public class SistemaServiceImpl implements SistemaService {

	@Autowired
	private SistemaDao sistemaDao;

	public void salvar(Sistema sistema) {
		sistemaDao.save(sistema);
	}

	public void remover(Integer id) {
		sistemaDao.remove(id);
	}

	public List<Sistema> listarTodos() {
		return sistemaDao.getAll();
	}

	public Sistema buscarPeloId(Integer id) {
		return sistemaDao.getById(id);
	}

	public Sistema buscarPeloNome(String nome) {
		return sistemaDao.getByNome(nome);
	}
	
	public void salvarArquivo(Versao versao, Arquivo arquivo){
		// TODO implementar servico para upload
	}
	
	public void baixarArquivo(Versao versao, Arquivo arquivo){
		// TODO implementar servico para download
	}

}
