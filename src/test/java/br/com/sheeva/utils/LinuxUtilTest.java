package br.com.sheeva.utils;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Test;

import br.com.sheeva.dominio.Servidor;

public class LinuxUtilTest {

	Servidor server = new Servidor("localhost", "127.0.0.1", 22, "robson",
			"m1r4nd4");

	@After
	public void finalizar() {
		File teste = new File("/tmp/enviar.sh");
		teste.delete();
		teste = new File("/tmp/executa.sh");
		teste.delete();
		teste = new File("/tmp/executaRemoto.sh");
		teste.delete();
		teste = new File("/tmp/arq1");
		teste.delete();
		teste = new File("/tmp/arq2");
		teste.delete();
		
	}

	@Test
	public void testEnviarArquivos() {
		LinuxUtil.enviarArquivos(server, "/home/robson/.sheeva/teste/arquivos/");
		File arq1 = new File("/tmp/arq1");
		File arq2 = new File("/tmp/arq2");
		assertTrue(arq1.exists() && !arq1.isDirectory());
		assertTrue(arq2.exists() && !arq2.isDirectory());
	}

	@Test
	public void testEnviarArquivo() {
		LinuxUtil.enviarArquivo(server, "/home/robson/.sheeva/teste/enviar.sh");
		File teste = new File("/tmp/enviar.sh");
		assertTrue(teste.exists() && !teste.isDirectory());
	}

	@Test
	public void testExecutarServidorRemoto() {
		LinuxUtil.executarServidorRemoto(server, "touch /tmp/executaRemoto.sh");
		File teste = new File("/tmp/executaRemoto.sh");
		assertTrue(teste.exists() && !teste.isDirectory());
	}

	@Test
	public void testExecuteCommand() {
		LinuxUtil.executeCommand("touch /tmp/executa.sh");
		File teste = new File("/tmp/executa.sh");
		assertTrue(teste.exists() && !teste.isDirectory());
	}
}
