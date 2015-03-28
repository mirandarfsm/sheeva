package br.com.sheeva.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.sheeva.conexao.ConexaoServidorSocket;
import br.com.sheeva.dominio.Servidor;
import br.com.sheeva.service.ConexaoService;

@Service("conexaoSocketService")
public class ConexaoSocketServiceImpl implements ConexaoService<ServerSocket>{

	private final String NAO_EXECUTADO = "O comando não foi executado.";
	
	@SuppressWarnings("resource")
	public ServerSocket abrirConexao(Servidor servidor) {
		try {
			ServerSocket server = ConexaoServidorSocket.getInstanceConection(8888);
			Socket cliente = server.accept();
			System.out.println("Nova conexão com o cliente " + cliente.getInetAddress().getHostAddress());
			Scanner scanner = new Scanner(cliente.getInputStream());
			while (scanner.hasNextLine()) {
				System.out.println(scanner.nextLine());
			}
			server.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Map<String, String> executarComandoRemoto(Servidor servidor, String comando) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, String> enviarArquivos(Servidor servidor, String diretorio) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("resource")
	public Map<String, String> enviarArquivo(Servidor servidor, String arquivo) {
		Map<String, String> saida = new HashMap<String, String>();
	    String mensagem = null;
		
		try {
			ServerSocket server = ConexaoServidorSocket.getInstanceConection(8888);
			Socket cliente = server.accept();
			System.out.println("Nova conexão com o cliente " + cliente.getInetAddress().getHostAddress());
			Scanner scanner = new Scanner(cliente.getInputStream());
			while (scanner.hasNextLine()) {
				mensagem = scanner.nextLine();
				if (mensagem.equals("AGUANDANDO_RECEBIMENTO")) {
					enviarArquivoOutputStream(arquivo, cliente);
				}
			}
			cliente.close();
			server.close();

		} catch (Exception e) {
			saida.put("NAO_EXECUTADO", NAO_EXECUTADO);
			e.printStackTrace();
		}
		return saida;
	}

	private void enviarArquivoOutputStream(String arquivo, Socket cliente) throws FileNotFoundException, IOException {
		File arquivoParaEnviar = new File (arquivo);
		byte [] mybytearray  = new byte [(int)arquivoParaEnviar.length()];
		
		FileInputStream fileInputStream = new FileInputStream(arquivoParaEnviar);
		BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
		bufferedInputStream.read(mybytearray,0,mybytearray.length);
		OutputStream outputStream = cliente.getOutputStream();
		
		System.out.println("Sending " + arquivo + "(" + mybytearray.length + " bytes)");
		outputStream.write(mybytearray,0,mybytearray.length);
		outputStream.flush();
		
		outputStream.close();
		bufferedInputStream.close();
		fileInputStream.close();
		System.out.println("Done.");
	}


}
