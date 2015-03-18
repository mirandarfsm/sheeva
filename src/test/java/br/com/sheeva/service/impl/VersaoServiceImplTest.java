package br.com.sheeva.service.impl;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Ignore;
import org.junit.Test;

import br.com.sheeva.dominio.Sistema;
import br.com.sheeva.dominio.Versao;
import br.com.sheeva.service.VersaoService;

public class VersaoServiceImplTest {

	Sistema system = new Sistema("Sheeva");;
	Versao version = new Versao(system, "1.0.0.0");
	VersaoService service = new VersaoServiceImpl();

	@Test
	public void salvarArquivo() throws IOException {
		InputStream is = new FileInputStream(
				"/home/robson/.sheeva/teste/sheeva/sheeva.sh");
		service.salvarArquivo(version, is, version + ".jpeg");
		File criado = new File(version.getFolder() + version + ".jpeg");
		assertTrue(criado.exists() && !criado.isDirectory());
	}

	@Ignore
	public void salvarArquivos() throws IOException {
		// TODO pegar implementacao de UploadFile
		String pathname = "/home/robson/.sheeva/teste/sheeva.jpeg";
		String pathname2 = "/home/robson/.sheeva/teste/sheeva.dia";

		// service.salvarArquivos(version, files);

		File criado = new File(version.getFolder() + version + ".jpeg");
		File criado2 = new File(version.getFolder() + version + ".dia");

		assertTrue(criado.exists() && !criado.isDirectory() && criado2.exists()
				&& !criado2.isDirectory());
	}
}
