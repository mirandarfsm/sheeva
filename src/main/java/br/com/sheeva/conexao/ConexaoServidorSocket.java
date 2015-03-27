package br.com.sheeva.conexao;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;

public class ConexaoServidorSocket extends ServerSocket{

	private static ServerSocket serverSocket;
	private static Map<Integer, ServerSocket> listaServerSocket = new HashMap<Integer, ServerSocket>();
	
	private ConexaoServidorSocket() throws IOException {
		super();
	}
	
	public static ServerSocket getInstanceConection(final Integer porta) throws IOException{
		if (!listaServerSocket.containsKey(porta)) {
			serverSocket = new ServerSocket(porta);
			listaServerSocket.put(porta, serverSocket);
		}
		return listaServerSocket.get(porta);
	}
	
}
