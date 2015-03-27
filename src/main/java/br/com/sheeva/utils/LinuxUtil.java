package br.com.sheeva.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import br.com.sheeva.dominio.Servidor;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class LinuxUtil {

	public static void enviarArquivos(Servidor servidor, String diretorio)
			throws IOException {
		File folder = new File(diretorio);
		for (File arquivo : folder.listFiles()) {
			enviarArquivo(servidor, arquivo.getPath());
		}
	}

	public static void enviarArquivo(Servidor servidor, String arquivo) throws IOException {
		Session session = null;
		ChannelSftp channel = null;
		File file = null;
		FileInputStream fis = null;
		try {
			JSch jsch = new JSch();
			session = jsch.getSession(servidor.getLogin(),servidor.getEndereco(), servidor.getPorta());
			session.setPassword(servidor.getSenha());
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			channel = (ChannelSftp) session.openChannel("sftp");
			channel.connect();
			channel.cd("/tmp/");
			file = new File(arquivo);
			fis = new FileInputStream(file);
			channel.put(fis, file.getName());
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (fis != null) {
				fis.close();
			}
			channel.disconnect();
			session.disconnect();
		}
	}

	public static String executeCommand(String command) {

		StringBuffer output = new StringBuffer();

		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					p.getInputStream()));

			String line = "";
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return output.toString();

	}

	public static Map<String, String> executarComandoRemoto(Servidor servidor, String comando){
		JSch jsch = new JSch();
		ChannelExec channel = null;
		Session session = null;
		StringBuffer result = new StringBuffer();
		StringBuffer saidaPadrao = null;
		StringBuffer saidaErro = null;
		Map<String, String> saida = new HashMap<String, String>();

		try {
			session = jsch.getSession(servidor.getLogin(), servidor.getEndereco(), servidor.getPorta());
			session.setPassword(servidor.getSenha());
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect(10*1000);
			channel= (ChannelExec) session.openChannel("exec");
			channel.setCommand(comando);
			channel.setInputStream(null);
			InputStream stdout = channel.getInputStream();
			InputStream stderr = channel.getErrStream();
			channel.connect();

			if (channel.getExitStatus() != 0) {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stderr));
				saidaErro = readAll(bufferedReader, result);
				saida.put("err", saidaErro.toString());
			} else {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stdout));
				saidaPadrao = readAll(bufferedReader, result);
				saida.put("out", saidaPadrao.toString());
			}

		} catch (JSchException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (channel.isConnected()) {
				channel.disconnect();
			}
			if (session.isConnected()) {
				session.disconnect();
			}
		}
		return saida;
	}

	private static StringBuffer readAll(BufferedReader bufferedReader, StringBuffer result) throws IOException {
		String msg=null;
		while((msg=bufferedReader.readLine())!=null){
			result.append(msg);
		}
		return result;
	}


}
