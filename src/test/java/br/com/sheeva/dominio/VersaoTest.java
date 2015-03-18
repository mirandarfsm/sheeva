package br.com.sheeva.dominio;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class VersaoTest {

	private Sistema system;

	@Before
	public void init() {
		system = new Sistema("Sheeva");
	}

	@Test
	public void criarVersao() {
		Versao version = new Versao(system, "1.0.0.0");

		assertEquals("1.0.0.0", version.getVersaoString());
		assertEquals("/home/robson/.sheeva/sistema/Sheeva/1.0.0.0/",
				version.getFolder());
	}

}
