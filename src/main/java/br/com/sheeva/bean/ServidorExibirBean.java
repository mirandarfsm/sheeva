package br.com.sheeva.bean;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.sheeva.dominio.ConfiguracaoServidor;
import br.com.sheeva.dominio.Instancia;
import br.com.sheeva.dominio.Servidor;
import br.com.sheeva.dominio.Versao;
import br.com.sheeva.service.InstanciaService;
import br.com.sheeva.service.ServidorService;
import br.com.sheeva.service.VersaoService;
import br.com.sheeva.utils.LayoutIndexManager;
import br.com.sheeva.utils.ManagedBeanUtils;
import br.com.sheeva.utils.VersaoUtils;

@Service("servidorExibirBean")
@Scope(value = "view")
public class ServidorExibirBean {

	private Servidor servidor;
	private Servidor servidor1;
	private Instancia instancia;
	private Versao versao;
	private List<Versao> versoes;
	private ConfiguracaoServidor configuracaoServidor;

	@Autowired
	private ServidorService servidorService;
	@Autowired
	private InstanciaService instanciaService;
	@Autowired
	private VersaoService versaoService;

	@PostConstruct
	public void init() {
		servidor = obterServidor();
		versoes = versaoService.listarTodos();
		LayoutIndexManager.atualizarIndice(1);
	}

	public Servidor obterServidor() {
		String id = ManagedBeanUtils.obterParametroRequest("id");
		return "novo".equals(id) ? new Servidor() : servidorService.buscarPeloId(Integer.valueOf(id));
	}

	public void atualizar() {
		instancia = instanciaService.buscarPeloId(instancia.getId());
		versao = versaoService.buscarPeloId(versao.getId());
		servidorService.atualizarInstancia(servidor, versao, instancia);
	}

	public void abrirTerminal() {
		ManagedBeanUtils.redirecionarUrlExterna(obterUrlSsh());
	}

	public String obterUrlSsh() {
		StringBuffer stringBuffer = new StringBuffer();
		return stringBuffer.append("ssh://").append(servidor.getLogin()).append(":").append(servidor.getSenha()).append("@")
				.append(servidor.getEndereco()).append(":").append(servidor.getPorta()).toString();
	}

	public Servidor getServidor() {
		return servidor;
	}

	public void setServidor(Servidor servidor) {
		this.servidor = servidor;
	}

	public Instancia getInstancia() {
		return instancia;
	}

	public void setInstancia(Instancia instancia) {
		this.instancia = instancia;
	}

	public Versao getVersao() {
		return versao;
	}

	public void setVersao(Versao versao) {
		this.versao = versao;
	}

	public List<Versao> getVersoes() {
		return versoes;
	}

	public void setVersoes(List<Versao> versoes) {
		this.versoes = versoes;
	}

	public Servidor getServidor1() {
		return servidor1;
	}

	public void setServidor1(Servidor servidor1) {
		this.servidor1 = servidor1;
	}

	public ConfiguracaoServidor getConfiguracaoServidor() {
		return configuracaoServidor;
	}

	public void setConfiguracaoServidor(ConfiguracaoServidor configuracaoServidor) {
		this.configuracaoServidor = configuracaoServidor;
	}

	public boolean isPossuiConexaoMonitoramento() {
		configuracaoServidor = servidorService.pegarConfiguracaoServidor(servidor);
		return configuracaoServidor == null ? false : true;
	}

	public boolean isServidorPossuiInstancia(){
		return !servidor.getInstancias().isEmpty();
	}
	
	public List<Versao> obterVersoesPeloSistema(Instancia instancia) {
		versoes = versaoService.obterVersoesPeloSistema(instancia.getId());
		Versao versaoDaInstancia = instanciaService.buscarPeloId(instancia.getId()).getVersao();
		versoes = VersaoUtils.obterVersoesDisponveisAtualizacao(versoes, versaoDaInstancia);
		return versoes;
	} 
}
