package br.com.sheeva.dominio;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SistemaTest {

	@Test
	public void criarSistemaComNome() {
		Sistema system = new Sistema("Sheeva");
		assertEquals("Sheeva", system.getNome());
		assertEquals("/home/robson/.sheeva/sistema/Sheeva/", system.getFolder());
	}

}
