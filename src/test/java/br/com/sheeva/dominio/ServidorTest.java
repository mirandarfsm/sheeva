package br.com.sheeva.dominio;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ServidorTest {

	@Test
	public void criarServidor() {
		Servidor server = new Servidor("terra", "127.0.0.1", 22, "sheeva", "123456");
		
		assertEquals("terra", server.getNome());
		assertEquals("127.0.0.1", server.getEndereco());
		assertEquals(22, server.getPorta(),00);
		assertEquals("sheeva", server.getLogin());
		assertEquals("123456", server.getSenha());
	}

}
