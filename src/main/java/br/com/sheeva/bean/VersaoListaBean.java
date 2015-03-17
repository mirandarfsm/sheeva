package br.com.sheeva.bean;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.sheeva.dominio.Sistema;
import br.com.sheeva.dominio.Versao;
import br.com.sheeva.service.SistemaService;
import br.com.sheeva.service.VersaoService;
import br.com.sheeva.utils.Mensagem;

@Service("versaoListaBean")
@Scope(value = "view")
public class VersaoListaBean {

	private Versao versao;
	private List<Versao> versoes;
	private List<Sistema> sistemas;

	@Autowired
	private VersaoService versaoService;

	@Autowired
	private SistemaService sistemaService;

	@SuppressWarnings("restriction")
	@PostConstruct
	public void init() {
		versoes = versaoService.listarTodos();
		sistemas = sistemaService.listarTodos();
	}

	public void excluir(Versao versao) {
		versaoService.remover(versao.getId());
		versaoService.deletarDiretorio(versao.getFolder());
		versoes = versaoService.listarTodos();
		Mensagem.msgInformacao("Versao excluído com sucesso");
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

	public List<Sistema> getSistemas() {
		return sistemas;
	}

	public void setSistemas(List<Sistema> sistemas) {
		this.sistemas = sistemas;
	}

}
