package br.com.sheeva.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;
import org.springframework.stereotype.Service;

import br.com.sheeva.conexao.ConexaoSocket;
import br.com.sheeva.dominio.Servidor;
import br.com.sheeva.dto.PacoteAtualizacaoDTO;
import br.com.sheeva.service.ConexaoService;

@Service("conexaoSocketService")
public class ConexaoSocketServiceImpl implements ConexaoService<ConexaoSocket>{

	private final String NAO_EXECUTADO = "O comando não foi executado.";

	public ConexaoSocket abrirConexao(Servidor servidor) {
		ConexaoSocket conexaoSocket = null;
		try {
			ServerSocket server = new ServerSocket(8888);
			Socket cliente = server.accept();
			conexaoSocket = new ConexaoSocket(server, cliente);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conexaoSocket;
	}

	public void acompanharAtualizacao(ConexaoSocket conexaoSocket, PacoteAtualizacaoDTO pacoteAtualizacaoDTO) {
		ServerSocket server = conexaoSocket.getServidorSocket();
		Socket cliente = conexaoSocket.getClientSocket();
		System.out.println("Nova conexão com o cliente " + cliente.getInetAddress().getHostAddress());
		// TODO atualizar painel
		Scanner scanner;
		try {
			scanner = new Scanner(cliente.getInputStream());
			while (scanner.hasNextLine()) {
				String mensagem = scanner.nextLine();
				JSONObject objetoJson =new JSONObject(mensagem);
				String key = objetoJson.keys().next().toString();
				switch (key) {
				case "properties":
					String variaveis = encapsulaVariaveis(pacoteAtualizacaoDTO);
					enviarString(variaveis, cliente);
					break;
				case "file":
					enviarArquivo(pacoteAtualizacaoDTO, cliente, objetoJson.getString(key));
					break;
				case "panel":
					System.out.println(objetoJson.get(key));
					break;
				default:
					break;
				}
			}
			cliente.close();
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void enviarString(String variaveis, Socket cliente) throws IOException {
		OutputStream outstream = cliente.getOutputStream(); 
		PrintWriter out = new PrintWriter(outstream);
		out.print(variaveis);
		out.flush();

		out.close();
		outstream.close();
	}

	private String encapsulaVariaveis(PacoteAtualizacaoDTO pacoteAtualizacaoDTO) throws JSONException {
		JSONObject objetoJson = new JSONObject();
		objetoJson.put("versao", pacoteAtualizacaoDTO.getVersao().getVersaoString());
		objetoJson.put("instancia", pacoteAtualizacaoDTO.getInstancia().getNome());
		objetoJson.put("aplicacao", pacoteAtualizacaoDTO.getVersao().getArquivoAplicacao());
		objetoJson.put("banco", pacoteAtualizacaoDTO.getVersao().getArquivoBancoDados());
		objetoJson.put("script", pacoteAtualizacaoDTO.getVersao().getArquivoScript());
		return objetoJson.toString();
	}

	public Map<String, String> executarComandoRemoto(Servidor servidor, String comando) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, String> enviarArquivos(Servidor servidor, String diretorio) {
		// TODO Auto-generated method stub
		return null;
	}

	private void enviarArquivo(
			PacoteAtualizacaoDTO pacoteAtualizacaoDTO, Socket cliente, String stringArquivo) throws FileNotFoundException, IOException {

		File arquivoParaEnviar = new File (obterArquivo(stringArquivo, pacoteAtualizacaoDTO));
		byte [] mybytearray  = new byte [(int)arquivoParaEnviar.length()];

		FileInputStream fileInputStream = new FileInputStream(arquivoParaEnviar);
		BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
		bufferedInputStream.read(mybytearray,0,mybytearray.length);
		OutputStream outputStream = cliente.getOutputStream();

		outputStream.write(mybytearray,0,mybytearray.length);
		outputStream.flush();

		outputStream.close();
		bufferedInputStream.close();
		fileInputStream.close();
	}

	private String obterArquivo(String stringArquivo, PacoteAtualizacaoDTO pacoteAtualizacaoDTO) {
		String arquivo = null;
		String diretorio = pacoteAtualizacaoDTO.getVersao().getFolder();
		switch (stringArquivo) {
		case "ARQUIVO_APLICACAO":
			arquivo =  pacoteAtualizacaoDTO.getVersao().getArquivoAplicacao();
			break;
		case "ARQUIVO_BANCO":
			arquivo =  pacoteAtualizacaoDTO.getVersao().getArquivoBancoDados();
			break;
		case "ARQUIVO_SCRIPT":
			arquivo =  pacoteAtualizacaoDTO.getVersao().getArquivoScript();
			break;
		default:
			break;
		}
		return diretorio + "/" + arquivo;
	}

	@Override
	public Map<String, String> enviarArquivo(Servidor servidor, String arquivo) {
		// TODO Auto-generated method stub
		return null;
	}


}
