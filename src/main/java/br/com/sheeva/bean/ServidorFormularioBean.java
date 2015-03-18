package br.com.sheeva.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.sheeva.dominio.Instancia;
import br.com.sheeva.dominio.Servidor;
import br.com.sheeva.dominio.Versao;
import br.com.sheeva.service.InstanciaService;
import br.com.sheeva.service.ServidorService;
import br.com.sheeva.service.VersaoService;
import br.com.sheeva.utils.ManagedBeanUtils;
import br.com.sheeva.utils.Mensagem;

@Service("servidorFormularioBean")
@Scope(value = "view")
public class ServidorFormularioBean {

	private Servidor servidor;
	private List<Servidor> servidores;
	private Instancia instancia;
	private Versao versao;
	private List<Versao> versoes;
	private List<Instancia> instanciaParaRemover;

	@Autowired
	private ServidorService servidorService;
	@Autowired
	private InstanciaService instanciaService;
	@Autowired
	private VersaoService versaoService;

	@PostConstruct
	public void init() {
		servidor = obterServidor();
		novaInstancia();
		servidores = servidorService.listarTodos();
		versoes = versaoService.listarTodos();
	}

	public Servidor obterServidor() {
		String id = ManagedBeanUtils.obterParametroRequest("id");
		return "novo".equals(id) ? new Servidor() : servidorService.buscarPeloId(Integer.valueOf(id));
	}

	public void salvar() {
		servidorService.salvar(servidor);
		if (!(instanciaParaRemover == null || instanciaParaRemover.isEmpty())) {
			removerInstanciasDoBanco(instanciaParaRemover);
		}
		if (servidor.getId() == null) {
			Mensagem.msgInformacao("Servidor salvo com sucesso");
		} else {
			Mensagem.msgInformacao("Servidor alterado com sucesso");
		}
		ManagedBeanUtils.redirecionar("/servidor");
	}
	
	public void alterarSenhaServidor() {
		servidorService.salvar(servidor);
		Mensagem.msgInformacao("Senha do servidor alterada com sucesso");
	}

	public void atualizar() {
		instancia = instanciaService.buscarPeloId(instancia.getId());
		versao = versaoService.buscarPeloId(versao.getId());
		servidorService.atualizarInstancia(servidor, versao, instancia);
	}

	public void cancelar() {
		ManagedBeanUtils.redirecionar("/servidor");
	}
	
	public void novaInstancia() {
		instancia = new Instancia();
	}

	public void adicionarInstancia() {
		servidor.getInstancias().add(instancia);
		novaInstancia();
	}

	public void removerInstancia(Instancia instancia) {
		servidor.getInstancias().remove(instancia);
		if (instanciaParaRemover == null) {
			instanciaParaRemover = new ArrayList<Instancia>();
		}
		instanciaParaRemover.add(instancia);
	}
	
	private void removerInstanciasDoBanco(List<Instancia> instanciaParaRemover) {
		for (Instancia instancia : instanciaParaRemover) {
			if (instancia.getId() != null) {
				instanciaService.remover(instancia.getId());
			}
		}
	}
	
	public boolean isServidorPossuiInstancia(){
		return !servidor.getInstancias().isEmpty();
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

}
