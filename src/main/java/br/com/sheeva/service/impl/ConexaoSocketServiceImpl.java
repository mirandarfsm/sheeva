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
import br.com.sheeva.dominio.Versao;
import br.com.sheeva.dto.PacoteAtualizacaoDTO;
import br.com.sheeva.service.ConexaoService;

@Service("conexaoSocketService")
public class ConexaoSocketServiceImpl implements ConexaoService<ConexaoSocket>{

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
		Scanner scanner;
		try {
			scanner = new Scanner(cliente.getInputStream());
			while (scanner.hasNextLine()) {
				String mensagem = scanner.nextLine();
				JSONObject objetoJson =new JSONObject(mensagem);
				enviarMensagemAoPainel(objetoJson.get("panel").toString());
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

	private void enviarMensagemAoPainel(String mensagem) {
		System.out.println(mensagem);
	}

	public void enviarArquivosJson(ConexaoSocket conexaoSocket, PacoteAtualizacaoDTO pacoteAtualizacaoDTO) {
		ServerSocket server = conexaoSocket.getServidorSocket();
		Socket cliente = conexaoSocket.getClientSocket();
		System.out.println("Nova conexão com o cliente " + cliente.getInetAddress().getHostAddress());
		// TODO atualizar painel
		Scanner scanner;
		try {
			scanner = new Scanner(cliente.getInputStream());
			while (scanner.hasNextLine()) {
				JSONObject objetoJson = new JSONObject();
				objetoJson = encapsulaVariaveisJson(objetoJson, pacoteAtualizacaoDTO);
				objetoJson = encapsulaArquivosJson(objetoJson, pacoteAtualizacaoDTO);

				OutputStream outputStream = cliente.getOutputStream();
				PrintWriter printWriter = new PrintWriter(outputStream);
				printWriter.write(objetoJson.toString());
				printWriter.flush();

				outputStream.close();
				printWriter.close();
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

	private JSONObject encapsulaVariaveisJson(JSONObject objetoJson, PacoteAtualizacaoDTO pacoteAtualizacaoDTO) throws JSONException {
		objetoJson.put("versao", pacoteAtualizacaoDTO.getVersao().getVersaoString());
		objetoJson.put("instancia", pacoteAtualizacaoDTO.getInstancia().getNome());
		objetoJson.put("aplicacao", pacoteAtualizacaoDTO.getVersao().getArquivoAplicacao());
		objetoJson.put("banco", pacoteAtualizacaoDTO.getVersao().getArquivoBancoDados());
		objetoJson.put("script", pacoteAtualizacaoDTO.getVersao().getArquivoScript());
		return objetoJson;
	}

	private JSONObject encapsulaArquivosJson(JSONObject objetoJson, PacoteAtualizacaoDTO pacoteAtualizacaoDTO)
			throws FileNotFoundException, IOException, JSONException {

		Map<String, String> arquivosDaVersao = obterArquivosDaVersao(pacoteAtualizacaoDTO.getVersao());
		for (String arquivoDaVersao : arquivosDaVersao.keySet()) {
			File arquivoParaEnviar = new File (arquivosDaVersao.get(arquivoDaVersao));
			byte [] mybytearray  = new byte [(int)arquivoParaEnviar.length()];
			FileInputStream fileInputStream = new FileInputStream(arquivoParaEnviar);
			BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
			bufferedInputStream.read(mybytearray,0,mybytearray.length);
			objetoJson.put(arquivoDaVersao, mybytearray);
		}
		return objetoJson;
	}

	public Map<String, String> executarComandoRemoto(Servidor servidor, String comando) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, String> enviarArquivos(Servidor servidor, String diretorio) {
		// TODO Auto-generated method stub
		return null;
	}

	private Map<String, String> obterArquivosDaVersao(Versao versao) {
		String diretorio = versao.getFolder();
		Map<String, String> arquivos = new HashMap<String, String>();
		arquivos.put("ARQUIVO_APLICACAO", diretorio + "/" + versao.getArquivoAplicacao());
		arquivos.put("ARQUIVO_BANCO", diretorio + "/" + versao.getArquivoBancoDados());
		arquivos.put("ARQUIVO_SCRIPT", diretorio + "/" + versao.getArquivoScript());

		return arquivos;
	}

	public Map<String, String> enviarArquivo(Servidor servidor, String arquivo) {
		// TODO Auto-generated method stub
		return null;
	}

}
