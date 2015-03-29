package br.com.sheeva.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.sheeva.dao.ServidorDao;
import br.com.sheeva.dao.VersaoDao;
import br.com.sheeva.dominio.Instancia;
import br.com.sheeva.dominio.Servidor;
import br.com.sheeva.dominio.Sistema;
import br.com.sheeva.dominio.Versao;
import br.com.sheeva.service.ConexaoService;
import br.com.sheeva.service.AtualizacaoService;

@Service("atualizacaoService")
public class AtualizacaoServiceImpl implements AtualizacaoService {

	@Autowired
	private ServidorDao servidorDao;

	@Autowired
	private VersaoDao versaoDao;

	@Autowired
	@Qualifier("conexaoSSHService")
	private ConexaoService<?> conexaoSSHService;

	@Autowired
	@Qualifier("conexaoSocketService")
	private ConexaoService<?> conexaoSocketService;

	public void atualizarInstancias(Servidor servidor, Versao versao) {
		for (Instancia instancia : servidor.getInstancias()) {
			atualizarInstancia(servidor, versao, instancia);
		}
	}

	public void atualizarInstancia(Servidor servidor, Versao versao, Instancia instancia) {
		// TODO Implementar -
		if (instancia.getVersao().getSistema().equals(versao.getSistema())) {
			List<Versao> versoes = versaoDao.getVersionList(instancia.getVersao(), versao);

			for (Versao v : versoes) {
				atualizarVersaoDaInstancia(servidor, v, instancia);
			}
		}
	}

	public void atualizarVersaoDaInstancia(Servidor servidor, Versao versao, Instancia instancia) {
		Sistema sistema = versao.getSistema();

		if (enviarArquivoAtualizacao(servidor, sistema)) {
			abrirConexaoThread(servidor, versao, instancia);
			executarComandoRemoto(servidor, sistema);
		}
	}

	private void executarComandoRemoto(Servidor servidor, Sistema sistema) {
		Map<String, String> saidaExecutarComando = conexaoSSHService.executarComandoRemoto(servidor,
				"python /tmp/" + sistema.getArquivoAtualizacao());
	}

	private boolean enviarArquivoAtualizacao(Servidor servidor, Sistema sistema) {
		
		Map<String, String> saidaEnvioDeArquivo = conexaoSSHService
				.enviarArquivo(servidor, sistema.getFolder() + "/" + sistema.getArquivoAtualizacao());
		
		if (saidaEnvioDeArquivo.containsKey("NAO_EXECUTADO")) {
			return false;
		}
		return true;
	}

	private void abrirConexaoThread(final Servidor servidor, final Versao versao, final Instancia instancia) {
		Runnable abrirConexao = new Runnable() {
			public void run() {
				conexaoSocketService.abrirConexao(servidor);
			}
		};
		Thread serverThread = new Thread(abrirConexao);
		serverThread.run();
	}

	public void alterarArquivoConfiguracao(Servidor servidor, Versao versao, String arquivoConfiguracao) {
		// TODO Auto-generated method stub
		
	}

	public String pegarArquivoConfiguracao(Servidor servidor, Versao versao) {
		// TODO Auto-generated method stub
		return null;
	}



	
}
