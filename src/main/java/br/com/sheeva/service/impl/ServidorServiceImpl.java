package br.com.sheeva.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.InstanceNotFoundException;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.sheeva.dao.ServidorDao;
import br.com.sheeva.dao.VersaoDao;
import br.com.sheeva.dominio.ConfiguracaoServidor;
import br.com.sheeva.dominio.Instancia;
import br.com.sheeva.dominio.Servidor;
import br.com.sheeva.dominio.Sistema;
import br.com.sheeva.dominio.Versao;
import br.com.sheeva.service.ConexaoService;
import br.com.sheeva.service.ServidorService;

@Service("servidorService")
public class ServidorServiceImpl implements ServidorService {

	@Autowired
	private ServidorDao servidorDao;

	@Autowired
	private VersaoDao versaoDao;

	@Autowired
	@Qualifier("conexaoJMXService")
	private ConexaoService<?> conexaoJMXService;

	@Autowired
	@Qualifier("conexaoSSHService")
	private ConexaoService<?> conexaoSSHService;
	
	@Autowired
	@Qualifier("conexaoSocketService")
	private ConexaoService<?> conexaoSocketService;

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
			atualizarInstancia(servidor, versao, instancia);
		}
	}

	public void atualizarInstancia(Servidor servidor, Versao versao,
			Instancia instancia) {
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
		
		Map<String, String> saidaEnvioDeArquivo = conexaoSSHService.enviarArquivo(servidor, sistema.getFolder()+"/"+sistema.getArquivoAtualizacao());
		if (saidaEnvioDeArquivo.containsKey("NAO_EXECUTADO")) {
			return;
		}
		abrirConexaoThread(servidor, versao, instancia);
		Map<String, String> saidaExecutarComando = conexaoSSHService.executarComandoRemoto(servidor, "python /tmp/" + sistema.getArquivoAtualizacao());
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

	public ConfiguracaoServidor obterConfiguracaoServidor(Servidor servidor) {
		String[] atributosOperatingSystem = {"Name", 
				"Version", 
				"Arch", 
				"AvailableProcessors", 
				"TotalPhysicalMemorySize", 
				"TotalSwapSpaceSize",
		"FreePhysicalMemorySize"};
		String nomeObjeto="java.lang:type=OperatingSystem";
		HashMap<String, Object> listaDeAtributos = pegarAtributosDoServidor(servidor, atributosOperatingSystem, nomeObjeto);
		return getConfiguracao(listaDeAtributos);
	}

	public ConfiguracaoServidor obterMemoria(Servidor servidor) {
		String[] atributosOperatingSystem = {"HeapMemoryUsage", "NonHeapMemoryUsage"};
		String nomeObjeto="java.lang:type=Memory";
		HashMap<String, Object> listaDeAtributos = pegarAtributosDoServidor(servidor, atributosOperatingSystem, nomeObjeto);
		return getConfiguracao(listaDeAtributos);
	}

	public HashMap<String, Object> pegarAtributosDoServidor(Servidor servidor, String[] atributosOperatingSystem, String nomeObjeto) {
		HashMap<String, Object> listaDeAtributos = new HashMap<String, Object>();

		JMXConnector conector = (JMXConnector) conexaoJMXService.abrirConexao(servidor);
		try {
			AttributeList attributeList = conector.getMBeanServerConnection().getAttributes(new ObjectName(nomeObjeto), atributosOperatingSystem);
			List<Attribute> lista = attributeList.asList();
			conector.close();

			for (Attribute atributo : lista) {
				listaDeAtributos.put(atributo.getName(), atributo.getValue());
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		} catch (MalformedObjectNameException e) {
			e.printStackTrace();
		} catch (ReflectionException e) {
			e.printStackTrace();
		}

		return listaDeAtributos;
	}

	private ConfiguracaoServidor getConfiguracao(HashMap<String, Object> listaDeAtributos) {
		if (listaDeAtributos.isEmpty()){
			return null;
		}
		ConfiguracaoServidor configuracaoServidor = new ConfiguracaoServidor();
		configuracaoServidor.setArquitetura((String) listaDeAtributos.get("Arch"));
		configuracaoServidor.setNumeroProcessadores((Integer) listaDeAtributos.get("AvailableProcessors"));
		configuracaoServidor.setMemoriaFisicaTotal((Long) listaDeAtributos.get("TotalPhysicalMemorySize"));
		configuracaoServidor.setMemoriaSwapTotal((Long) listaDeAtributos.get("TotalSwapSpaceSize"));
		configuracaoServidor.setMemoriaFisicaLivre((Long) listaDeAtributos.get("FreePhysicalMemorySize"));
		configuracaoServidor.setSistemaOperacional(listaDeAtributos.get("Name") + " " +  listaDeAtributos.get("Version"));

		return configuracaoServidor;
	}

}
