package br.com.sheeva.bean;

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
import br.com.sheeva.utils.Mensagem;

@Service("servidorListaBean")
@Scope(value = "view")
public class ServidorListaBean {

	private Servidor servidor;
	private Servidor servidor1;
	private List<Servidor> servidores;
	private Instancia instancia;
	private Versao versao;
	private List<Versao> versoes;

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
	}

	public void excluir(Servidor servidor) {
		servidorService.remover(servidor.getId());
		servidores = servidorService.listarTodos();
		Mensagem.msgInformacao("Servidor excluído com sucesso");
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

}
