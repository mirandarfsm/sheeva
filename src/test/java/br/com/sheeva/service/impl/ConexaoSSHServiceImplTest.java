package br.com.sheeva.service.impl;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import br.com.sheeva.dominio.Servidor;
import br.com.sheeva.service.ConexaoService;

@ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class ConexaoSSHServiceImplTest implements Runnable{

	@Autowired
	private WebApplicationContext context;

	@Autowired
	@Qualifier("conexaoSSHService")
	private ConexaoService<?> conexaoSSHService;

	private Servidor servidor;

	@Before
	public void iniciar() {
		servidor = new Servidor("Sigadaer", "192.168.1.39", 22, "root", "123456");
	}

	@Test
	public void atualizarVersaoDaInstancia() {
		//String comandoCriarArquivo = "touch /tmp/teste";
		String comandoListar = "ls -lh /tmp/";
		//String comandoIniciarServidor = "/etc/init.d/catalina start";
		Map<String, String> saida = conexaoSSHService.executarComando(servidor, comandoListar);
	}

	@After
	public void finalizar(){

	}

	public void run() {

	}


}
