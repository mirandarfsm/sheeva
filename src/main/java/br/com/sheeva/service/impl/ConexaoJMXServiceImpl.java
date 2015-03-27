package br.com.sheeva.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.springframework.stereotype.Service;

import br.com.sheeva.dominio.Servidor;
import br.com.sheeva.service.ConexaoService;

@Service("conexaoJMXService")
public class ConexaoJMXServiceImpl implements ConexaoService<JMXConnector>{

	public JMXConnector abrirConexao(Servidor servidor) {
		HashMap<String, String[]> map = new HashMap<String, String[]>();
		String[] credentials = new String[2];
		//credentials[0] = user;
		//credentials[1] = password;
		map.put("jmx.remote.credentials", credentials);
		JMXConnector conector = null;
		try {
			conector = JMXConnectorFactory.newJMXConnector(
					criarURLConexao(servidor.getEndereco(), servidor.getPortaMonitoramento().intValue()), map);
			conector.connect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return conector;
	}

	private static JMXServiceURL criarURLConexao(String endereco, int porta) throws MalformedURLException {
		return new JMXServiceURL("rmi", "", 0, "/jndi/rmi://" + endereco + ":" + porta + "/jmxrmi");
	}

	public Map<String, String> executarComando(Servidor servidor, String comando) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
