package br.com.sheeva.conexao;

import java.net.ServerSocket;
import java.net.Socket;

public class ConexaoSocket {

	private ServerSocket servidorSocket;
	private Socket clientSocket;

	@SuppressWarnings("unused")
	private ConexaoSocket(){
		
	}
	
	public ConexaoSocket(ServerSocket servidorSocket, Socket clientSocket) {
		super();
		this.servidorSocket = servidorSocket;
		this.clientSocket = clientSocket;
	}

	public ServerSocket getServidorSocket() {
		return servidorSocket;
	}

	public Socket getClientSocket() {
		return clientSocket;
	}

}
