package br.com.sheeva.bean;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.sheeva.dominio.Instancia;
import br.com.sheeva.dominio.Servidor;
import br.com.sheeva.service.InstanciaService;
import br.com.sheeva.service.ServidorService;
import br.com.sheeva.utils.ManagedBeanUtils;
import br.com.sheeva.utils.Mensagem;

@Service("arquivoConfiguracaoBean")
@Scope(value = "session")
public class ArquivoConfiguracaoBean {

	private String configuracao;
	private Instancia instancia;
	private Servidor servidor;
	private List<Servidor> servidores;

	@Autowired
	private InstanciaService instanciaService;

	@Autowired
	private ServidorService servidorService;
	
	@Autowired
	private InstanciaBean instanciaBean;

	@PostConstruct
	public void init() {
		servidores = servidorService.listarTodos();
		instancia = instanciaBean.getInstancia();
		servidor = servidores.get(0);
		configuracao = instanciaService.getArquivoConfiguracao(servidor, instancia.getDiretorioPrincipal()+instancia.getArquivoConfiguracao()).replaceAll("\n", "<br>");
	}
	
	public void salvar(){
		instanciaService.setArquivoConfiguracao(servidor, instancia.getDiretorioPrincipal()+instancia.getArquivoConfiguracao(),configuracao.replaceAll("<br>" , "\n"));
		Mensagem.msgInformacao("Arquivo Configuracao alterado com sucesso");
		ManagedBeanUtils.redirecionar("/instancia");
	}
	
	public void submit(){
		configuracao = instanciaService.getArquivoConfiguracao(servidor,instancia.getArquivoConfiguracao()).replaceAll("\n", "<br>");
	}

	public void cancelar() {
		ManagedBeanUtils.redirecionar("/instancia");
	}
	
	public String getConfiguracao() {
		return configuracao;
	}

	public void setConfiguracao(String configuracao) {
		this.configuracao = configuracao;
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

}
