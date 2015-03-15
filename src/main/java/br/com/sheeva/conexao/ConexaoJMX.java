package br.com.sheeva.conexao;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;

import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;


public class ConexaoJMX {
	
	public static JMXConnector getConexao(String endereco, int porta) throws MalformedURLException, IOException{
		HashMap<String, String[]> map = new HashMap<String, String[]>();
		String[] credentials = new String[2];
		//credentials[0] = user;
		//credentials[1] = password;
		map.put("jmx.remote.credentials", credentials);
		JMXConnector conector = JMXConnectorFactory.newJMXConnector(criarURLConexao(endereco, porta), map);
		conector.connect();
		return conector;
		
	}
	
	private static JMXServiceURL criarURLConexao(String endereco, int porta) throws MalformedURLException {
		return new JMXServiceURL("rmi", "", 0, "/jndi/rmi://" + endereco + ":" + porta + "/jmxrmi");
	}

	

}
