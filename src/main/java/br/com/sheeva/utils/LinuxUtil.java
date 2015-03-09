package br.com.sheeva.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import br.com.sheeva.dominio.Servidor;

public class LinuxUtil {

	public static String enviarArquivos(Servidor servidor, String diretorio) {
		StringBuilder command = new StringBuilder();
		command.append("sshpass -p ").append(servidor.getSenha()).append(" scp ")
				.append(" -P ").append(servidor.getPorta())
				.append(" " + diretorio + "* ").append(servidor.getLogin())
				.append("@").append(servidor.getEndereco()).append(":/tmp");

		String output = executeCommand(command.toString());
		return output;
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

}
