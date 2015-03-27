package br.com.sheeva.service.impl;

import java.io.IOException;

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
import br.com.sheeva.utils.LinuxUtil;

@ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class ConexaoSocketServiceImplTest implements Runnable{

	@Autowired
	private WebApplicationContext context;

	@Autowired
	@Qualifier("conexaoSocketService")
	private ConexaoService<?> conexaoSocketService;

	@Autowired
	@Qualifier("conexaoSSHService")
	private ConexaoService<?> conexaoSSHService;

	private Servidor servidor;

	@Before
	public void iniciar() {
		servidor = new Servidor("Sigadaer", "192.168.1.40", 22, "root", "123456");
	}

	@Test
	public void atualizarVersaoDaInstancia() throws IOException {
		 Runnable serverTask = new Runnable() {
	            
	            public void run() {
                    conexaoSocketService.abrirConexao(servidor);
	            }
	        };
	        Thread serverThread = new Thread(serverTask);
	        serverThread.start();
		
		
		Runnable executarScriptRemoto = new Runnable() {
			public void run() {
				//String comandoConectar = "python /tmp/conectar.py";
				String comandoCriarArquivo = "touch /tmp/teste";
				String comandoIniciarServidor = "/etc/init.d/catalina start";
				conexaoSSHService.executarComandoRemoto(servidor, comandoCriarArquivo);
			}
		};
		executarScriptRemoto.run();

	}

	@After
	public void finalizar(){

	}

	public void run() {

	}


}
