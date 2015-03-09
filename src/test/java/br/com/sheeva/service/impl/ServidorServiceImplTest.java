package br.com.sheeva.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.After;
import org.junit.Test;

import br.com.sheeva.dominio.Instancia;
import br.com.sheeva.dominio.Servidor;
import br.com.sheeva.dominio.Sistema;
import br.com.sheeva.dominio.Versao;
import br.com.sheeva.service.ServidorService;

public class ServidorServiceImplTest {

	Sistema system = new Sistema("sheeva");;
	Versao version = new Versao(system, "1.0");
	Servidor server = new Servidor("localhost", "127.0.0.1", 22, "robson","m1r4nd4");
	Instancia instance = new Instancia("sheeva", "/tmp/", "sheeva.conf");
	ServidorService service = new ServidorServiceImpl();
	
	@After
	public void finalizar(){
		File teste = new File("/tmp/sheeva.versao");
		teste.delete();
	}

	@Test
	public void testAtualizarInstancias() {

	}

	@Test
	public void testAtualizarInstancia() {
	}

	@Test
	public void atualizarVersaoDaInstancia() throws IOException {
		instance.setVersao(new Versao(system, "0.0"));
		service.atualizarVersaoDaInstancia(server, version, instance);
		BufferedReader br = new BufferedReader(new FileReader("/tmp/sheeva.versao"));

		while (br.ready()) {
			String linha = br.readLine();
			System.out.println(linha);
		}
		br.close();
	}

	@Test
	public void testAlterarArquivoConfiguracao() {
	}

	@Test
	public void testPegarArquivoConfiguracao() {
	}

	@Test
	public void testReiniciarServidorWeb() {
	}

	@Test
	public void testReiniciarAplicacao() {
	}

	@Test
	public void testPegarConfiguracaoServidor() {
	}

}
