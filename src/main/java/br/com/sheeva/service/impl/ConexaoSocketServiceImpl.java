package br.com.sheeva.service.impl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.sheeva.conexao.ConexaoServidorSocket;
import br.com.sheeva.dominio.Servidor;
import br.com.sheeva.service.ConexaoService;

@Service("conexaoSocketService")
public class ConexaoSocketServiceImpl implements ConexaoService<ConexaoServidorSocket>{

	public ConexaoServidorSocket abrirConexao(Servidor servidor) {
		try {
			ConexaoServidorSocket conexao = new ConexaoServidorSocket();
			ServerSocket server = new ServerSocket(8888);
			Socket cliente = server.accept();
			System.out.println("Nova conexão com o cliente " + cliente.getInetAddress().getHostAddress());
			Scanner scanner = new Scanner(cliente.getInputStream());
			while (scanner.hasNextLine()) {
				System.out.println(scanner.nextLine());
			}

			cliente.close();
			server.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


}
