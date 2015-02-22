package br.com.sheeva.service.impl;

import java.util.LinkedList;
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

	public void atualizarInstancias(Servidor servidor, Versao versao) {
		for (Instancia instancia : servidor.getInstancias()) {
			if (instancia.getVersao().getSistema().equals(versao.getSistema())) {
				atualizarInstancia(servidor, versao, instancia);
			}
		}
	}

	public void atualizarInstancia(Servidor servidor, Versao versao,
			Instancia instancia) {
		// TODO Implementar -
		// versaoDao.getVersoes(instancia.getVersao(),versao);
		List<Versao> versoes = new LinkedList<Versao>();
		for (Versao v : versoes) {
			LinuxUtil.enviarArquivos(servidor, versao.getFolder());
			StringBuffer command = new StringBuffer();
			command.append(
					"bash /tmp/" + versao.getSistema().getNome() + ".sh ")
					.append(instancia.getNome()).append(v.getVersao());
			LinuxUtil.executarServidorRemoto(servidor, command.toString());
		}
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
		// TODO implementar script para pegar dados do servidor

	}

}
