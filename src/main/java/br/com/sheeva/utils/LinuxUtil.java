package br.com.sheeva.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import br.com.sheeva.dominio.Servidor;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class LinuxUtil {

	public static String enviarArquivos(Servidor servidor, String diretorio) {
		File folder = new File(diretorio);
		StringBuffer log = new StringBuffer();
		for(File arquivo : folder.listFiles()){
			log.append(enviarArquivo(servidor, arquivo.getPath()));
		}
		return log.toString();
	}

	public static String enviarArquivo(Servidor servidor, String arquivo) {
		StringBuilder command = new StringBuilder();
		command.append("sshpass -p ").append(servidor.getSenha()).append(" scp ")
		.append(" -P ").append(servidor.getPorta()).append(" ")
		.append(arquivo).append(" ").append(servidor.getLogin())
		.append("@").append(servidor.getEndereco()).append(":/tmp/");

		String output = executeCommand(command.toString());
		return output;
	}


	public static String executarServidorRemoto(Servidor servidor, String command) {
		StringBuilder remote = new StringBuilder();
		remote.append("sshpass -p ").append(servidor.getSenha()).append(" ssh ").append(" -l ")
		.append(servidor.getLogin()).append(" ").append(servidor.getEndereco())
		.append(" -p ").append(servidor.getPorta())
		.append(" -C  ").append(command).append("  ");

		String output = executeCommand(remote.toString());
		return output;
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

	public static void executarComandoRemoto(Servidor servidor, String comando){
		JSch jsch = new JSch();
		ChannelExec channel = null;
		Session session = null;

		try {
			session = jsch.getSession(servidor.getLogin(), servidor.getEndereco(), servidor.getPorta());
			session.setPassword(servidor.getSenha());
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect(10*1000);
			channel= (ChannelExec) session.openChannel("exec");
			BufferedReader in=new BufferedReader(new InputStreamReader(channel.getInputStream()));
			channel.setCommand(comando);
			channel.connect();

			String msg=null;
			while((msg=in.readLine())!=null){
				System.out.println(msg);
			}

		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			channel.disconnect();
			session.disconnect();
		}

	}

}
