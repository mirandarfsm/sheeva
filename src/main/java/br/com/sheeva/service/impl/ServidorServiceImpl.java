package br.com.sheeva.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.InstanceNotFoundException;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sheeva.conexao.ConexaoJMX;
import br.com.sheeva.dao.ServidorDao;
import br.com.sheeva.dao.VersaoDao;
import br.com.sheeva.dominio.ConfiguracaoServidor;
import br.com.sheeva.dominio.Instancia;
import br.com.sheeva.dominio.Servidor;
import br.com.sheeva.dominio.Versao;
import br.com.sheeva.service.ServidorService;
import br.com.sheeva.utils.LinuxUtil;

@Service("servidorService")
public class ServidorServiceImpl implements ServidorService {

	@Autowired
	private ServidorDao servidorDao;

	@Autowired
	private VersaoDao versaoDao;

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
		List<Versao> versoes = versaoDao.getVersionList(instancia.getVersao()
				.getId(), versao.getId());
		for (Versao v : versoes) {
			atualizarVersaoDaInstancia(servidor, v, instancia);
		}
	}

	public void atualizarVersaoDaInstancia(Servidor servidor, Versao versao,
			Instancia instancia) {
		LinuxUtil.enviarArquivos(servidor, versao.getFolder());
		StringBuffer command = new StringBuffer();
		command.append("bash /tmp/" + versao.getSistema().getNome() + ".sh ")
				.append(instancia.getNome()).append(versao.getVersao());
		LinuxUtil.executarServidorRemoto(servidor, command.toString());

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

	public ConfiguracaoServidor pegarConfiguracaoServidor(Servidor servidor) {
		String[] atributosOperatingSystem = {"Name", "Version", "Arch", "AvailableProcessors", "TotalPhysicalMemorySize", "TotalSwapSpaceSize"};
		HashMap<String, Object> listaDeAtributos = new HashMap<String, Object>();

		try {
			JMXConnector conector = ConexaoJMX.getConexao(servidor.getEndereco(), servidor.getPortaMonitoramento());
			
			AttributeList attributeList = conector.getMBeanServerConnection().getAttributes(new ObjectName("java.lang:type=OperatingSystem"), atributosOperatingSystem);
			List<Attribute> lista = attributeList.asList();

			for (Attribute atributo : lista) {
				listaDeAtributos.put(atributo.getName(), atributo.getValue());
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedObjectNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ReflectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return getConfiguracao(listaDeAtributos);
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
		configuracaoServidor.setSistemaOperacional(listaDeAtributos.get("Name") + " " +  listaDeAtributos.get("Version"));
		
		return configuracaoServidor;
	}

}
