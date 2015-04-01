package br.com.sheeva.service.impl;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import br.com.sheeva.dominio.Instancia;
import br.com.sheeva.dominio.Servidor;
import br.com.sheeva.dominio.Sistema;
import br.com.sheeva.dominio.Versao;
import br.com.sheeva.dto.PacoteAtualizacaoDTO;
import br.com.sheeva.service.ConexaoService;
import br.com.sheeva.utils.PainelAtualizacao;

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

	private Sistema sistema;

	private Versao versao;

	private Instancia instancia;

	private PainelAtualizacao painelAtualizacao;

	private PacoteAtualizacaoDTO pacoteAtualizacaoDTO;

	@Before
	public void iniciar() {
		servidor = new Servidor("Sigadaer", "192.168.1.39", 22, "root", "123456");
		sistema = new Sistema("Sigadaer");
		sistema.setArquivoAtualizacao("sigadaer.py");
		versao = new Versao();
		versao.setSistema(sistema);
		versao.setArquivoAplicacao("sigad-celog.war");
		versao.setArquivoBancoDados("sigad-celog.sql");
		versao.setArquivoScript("sigad-celog.sh");
		versao.setVersaoString("5.0.0.14");
		instancia = new Instancia("sigad-celog", "/var/sigadaer/sigad-celog/", "config.properties");
		
		painelAtualizacao = new PainelAtualizacao();
		pacoteAtualizacaoDTO = new PacoteAtualizacaoDTO(servidor, versao, sistema, painelAtualizacao, instancia);
	}

	@Test
	public void atualizarVersao() {
		conexaoSocketService.enviarArquivosJson(pacoteAtualizacaoDTO);
		//enviarArquivosJsonThread();
		//acompanharAtualizacaoThread();
	}
	
	@Ignore
	public void atualizarVersaoDaInstancia() {
		Sistema sistema = versao.getSistema();
		
		Map<String, String> saidaEnvioDeArquivo = conexaoSSHService.enviarArquivo(servidor, sistema.getFolder()+"/"+sistema.getArquivoAtualizacao());
		if (saidaEnvioDeArquivo.containsKey("NAO_EXECUTADO")) {
			return;
		}
		
		Map<String, String> saidaExecutarComando = conexaoSSHService.executarComandoRemoto(servidor, "python /tmp/" + sistema.getArquivoAtualizacao());
	}

	private void acompanharAtualizacaoThread() {
		conexaoSocketService.acompanharAtualizacao(pacoteAtualizacaoDTO);
	}
	
	private void enviarArquivosJsonThread() {
		Runnable abrirConexao = new Runnable() {
			public void run() {
				System.out.println("hi");
				conexaoSocketService.enviarArquivosJson(pacoteAtualizacaoDTO);
			}
		};
		Thread serverThread = new Thread(abrirConexao);
		serverThread.start();
	}

	@After
	public void finalizar(){

	}

	public void run() {

	}


}
