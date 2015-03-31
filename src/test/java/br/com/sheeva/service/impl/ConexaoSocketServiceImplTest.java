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

import br.com.sheeva.conexao.ConexaoSocket;
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
		versao.setArquivoAplicacao("sigad-ccasj.war");
		versao.setArquivoBancoDados("sigad-ccasj.sql");
		versao.setArquivoScript("sigad-ccasj.sh");
		versao.setVersaoString("5.0.0.14");
		instancia = new Instancia("sigad-ccasj", "/var/sigadaer/sigad-ccasj/", "config.properties");
		
		painelAtualizacao = new PainelAtualizacao();
		pacoteAtualizacaoDTO = new PacoteAtualizacaoDTO(servidor, versao, sistema, painelAtualizacao, instancia);
	}

	@Test
	public void atualizarVersao() {
		ConexaoSocket conexaoSocket = (ConexaoSocket) conexaoSocketService.abrirConexao(servidor);
		//conexaoSocketService.acompanharAtualizacao(conexaoSocket, pacoteAtualizacaoDTO);
		conexaoSocketService.enviarArquivosJson(conexaoSocket, pacoteAtualizacaoDTO);
	}
	
	@Ignore
	public void atualizarVersaoDaInstancia() {
		Sistema sistema = versao.getSistema();
		
		Map<String, String> saidaEnvioDeArquivo = conexaoSSHService.enviarArquivo(servidor, sistema.getFolder()+"/"+sistema.getArquivoAtualizacao());
		if (saidaEnvioDeArquivo.containsKey("NAO_EXECUTADO")) {
			return;
		}
		
		abrirConexaoThread(servidor, versao, instancia);
		Map<String, String> saidaExecutarComando = conexaoSSHService.executarComandoRemoto(servidor, "python /tmp/" + sistema.getArquivoAtualizacao());
	}

	private void abrirConexaoThread(final Servidor servidor, final Versao versao, final Instancia instancia) {
		Runnable abrirConexao = new Runnable() {
			public void run() {
				conexaoSocketService.abrirConexao(servidor);
			}
		};
		Thread serverThread = new Thread(abrirConexao);
		serverThread.run();
	}

	@After
	public void finalizar(){

	}

	public void run() {

	}


}
