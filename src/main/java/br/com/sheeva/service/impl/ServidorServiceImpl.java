package br.com.sheeva.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sheeva.dao.ServidorDao;
import br.com.sheeva.dominio.Instancia;
import br.com.sheeva.dominio.Servidor;
import br.com.sheeva.dominio.Versao;
import br.com.sheeva.service.ServidorService;
import br.com.sheeva.utils.LinuxUtil;

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

	public void atualizarInstancia(Servidor servidor, Versao versao) {
		LinuxUtil.enviarArquivos(servidor, versao.getArquivos());
		
	}

	public void alterarArquivoConfiguracao(Servidor servidor, Versao versao,
			String arquivoConfiguracao) {
		// TODO Auto-generated method stub
		
	}

	public String pegarArquivoConfiguracao(Servidor servidor, Versao versao) {
		// TODO Auto-generated method stub
		return null;
	}

	public void reiniciarServidorWeb(Servidor servidor, boolean work) {
		// TODO Auto-generated method stub
		
	}

	public void reiniciarAplicacao(Servidor servidor, Instancia instancia) {
		// TODO Auto-generated method stub
		
	}

	public void pegarConfiguracaoServidor(Servidor servidor) {
		// TODO Auto-generated method stub
		
	}

	
}
