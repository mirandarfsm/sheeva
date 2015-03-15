package br.com.sheeva.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.TabChangeEvent;
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
import br.com.sheeva.utils.Mensagem;

@Service("servidorBean")
@Scope(value = "session")
public class ServidorBean {

	private Servidor servidor;
	private Servidor servidor1;
	private List<Servidor> servidores;
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
		servidores = servidorService.listarTodos();
		versoes = versaoService.listarTodos();
		LayoutIndexManager.atualizarIndice(1);
	}

	public String novo() {
		servidor = new Servidor();
		return "/pages/servidor/cadastrar-servidor-formulario.xhtml";
	}

	public void salvar() {
		servidorService.salvar(servidor);
		if (servidor.getId() == null) {
			Mensagem.msgInformacao("Servidor salvo com sucesso");
		} else {
			Mensagem.msgInformacao("Servidor alterado com sucesso");
		}
		servidores = servidorService.listarTodos();
		ManagedBeanUtils.redirecionar("/servidor");
	}

	public void excluir() {
		servidorService.remover(servidor.getId());
		servidores = servidorService.listarTodos();
		Mensagem.msgInformacao("Servidor excluído com sucesso");
	}

	public String editar() {
		servidor = servidorService.buscarPeloId(servidor.getId());
		return "/pages/servidor/cadastrar-servidor-formulario.xhtml";
	}

	public String exibir() {
		servidor = servidorService.buscarPeloId(servidor.getId());
		return "/pages/servidor/exibir-servidor.xhtml";
	}

	public void atualizar() {
		instancia = instanciaService.buscarPeloId(instancia.getId());
		versao = versaoService.buscarPeloId(versao.getId());
		servidorService.atualizarInstancia(servidor, versao, instancia);
	}

	public void cancelar() {
		ManagedBeanUtils.redirecionar("/servidor");
	}

	public void adicionarInstancia() {
		servidor.getInstancias().add(new Instancia());
	}

	public void removerInstancia(Instancia instancia) {
		servidor.getInstancias().remove(instancia);
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

	public List<Servidor> getServidores() {
		return servidores;
	}

	public void setServidores(List<Servidor> servidores) {
		this.servidores = servidores;
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

	public void onTabChange(TabChangeEvent event) {
		FacesMessage msg = new FacesMessage("Tab Changed", "Active Tab" + event.getTab().getId());
		FacesContext.getCurrentInstance().addMessage(null, msg);
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

}
