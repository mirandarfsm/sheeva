package br.com.sheeva.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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

	public static void executarComandoRemoto(Servidor servidor, String comando) {
		JSch jsch = new JSch();
		ChannelExec channel = null;
		Session session = null;

		try {
			session = jsch.getSession(servidor.getLogin(),
					servidor.getEndereco(), servidor.getPorta());
			session.setPassword(servidor.getSenha());
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect(10 * 1000);
			channel = (ChannelExec) session.openChannel("exec");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					channel.getInputStream()));
			channel.setCommand(comando);
			channel.connect();

			String msg = null;
			while ((msg = in.readLine()) != null) {
				System.out.println(msg);
			}

		} catch (JSchException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			channel.disconnect();
			session.disconnect();
		}

	}

}
