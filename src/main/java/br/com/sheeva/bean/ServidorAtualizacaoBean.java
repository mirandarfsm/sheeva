package br.com.sheeva.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.sheeva.dominio.Instancia;
import br.com.sheeva.dominio.Servidor;
import br.com.sheeva.dominio.Versao;
import br.com.sheeva.service.AtualizacaoService;
import br.com.sheeva.service.InstanciaService;
import br.com.sheeva.service.ServidorService;
import br.com.sheeva.service.VersaoService;
import br.com.sheeva.utils.ManagedBeanUtils;
import br.com.sheeva.utils.PainelAtualizacao;
import br.com.sheeva.utils.VersaoUtils;

@Service("servidorAtualizacaoBean")
@Scope(value = "view")
public class ServidorAtualizacaoBean implements Serializable, Observer {

	private static final long serialVersionUID = 1L;
	private Servidor servidor;
	private Instancia instancia;
	private Versao versao;
	private List<Versao> versoes;

	@Autowired
	private ServidorService servidorService;
	@Autowired
	private InstanciaService instanciaService;
	@Autowired
	private VersaoService versaoService;
	@Autowired
	private Observable painelAtualizacao;
	@Autowired
	private AtualizacaoService atualizacaoService;

	@PostConstruct
	public void init() {
		servidor = obterServidor();
		versoes = versaoService.listarTodos();
		painelAtualizacao.addObserver(this);
	}

	public Servidor obterServidor() {
		String id = ManagedBeanUtils.obterParametroRequest("id");
		return "novo".equals(id) ? new Servidor() : servidorService.buscarPeloId(Integer.valueOf(id));
	}
	
	private void atualizarInstancia() {
		
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

	public List<Versao> obterVersoesPeloSistema(Instancia instancia) {
		versoes = versaoService.obterVersoesPeloSistema(instancia.getId());
		Versao versaoDaInstancia = instanciaService.buscarPeloId(instancia.getId()).getVersao();
		versoes = VersaoUtils.obterVersoesDisponveisAtualizacao(versoes, versaoDaInstancia);
		return versoes;
	}
	
	

	public void update(Observable painelAtualizacao, Object arg) {
		if (painelAtualizacao instanceof PainelAtualizacao) {
			
		}

	}
}
