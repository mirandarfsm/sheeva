package br.com.sheeva.bean;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.component.fileupload.FileUpload;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.sheeva.dominio.Arquivo;
import br.com.sheeva.dominio.Sistema;
import br.com.sheeva.dominio.Versao;
import br.com.sheeva.service.SistemaService;
import br.com.sheeva.service.VersaoService;
import br.com.sheeva.utils.LayoutIndexManager;
import br.com.sheeva.utils.ManagedBeanUtils;
import br.com.sheeva.utils.Mensagem;

@Service("versaoBean")
@Scope(value = "session")
public class VersaoBean {

	private Versao versao;
	private List<Versao> versoes;
	private List<Sistema> sistemas;
	private UploadedFile arquivo;
	private List<UploadedFile> arquivos;

	@Autowired
	private VersaoService versaoService;

	@Autowired
	private SistemaService sistemaService;

	@PostConstruct
	public void init() {
		versoes = versaoService.listarTodos();
		sistemas = sistemaService.listarTodos();
		arquivos = new LinkedList<UploadedFile>();
		LayoutIndexManager.atualizarIndice(1);
	}

	public String novo() {
		versao = new Versao();
		return "/pages/versao/cadastrar-versao-formulario.xhtml";
	}

	public void salvar() {
		versaoService.salvar(versao);
		if (versao.getId() == null) {
			Mensagem.msgInformacao("Versao salvo com sucesso");
		} else {
			Mensagem.msgInformacao("Versao alterado com sucesso");
		}
		versoes = versaoService.listarTodos();
		ManagedBeanUtils.redirecionar("/versao");
	}

	public void excluir() {
		versaoService.remover(versao.getId());
		versoes = versaoService.listarTodos();
		Mensagem.msgInformacao("Versao excluído com sucesso");
	}

	public String editar() {
		versao = versaoService.buscarPeloId(versao.getId());
		return "/pages/versao/cadastrar-versao-formulario.xhtml";
	}

	public void cancelar() {
		ManagedBeanUtils.redirecionar("/versao");
	}

	public void handleFileUpload(FileUploadEvent event) throws IOException {
		arquivo = event.getFile();
		versaoService.salvarArquivo(versao, arquivo.getInputstream(), arquivo.getFileName());
	}

	public void adicionarArquivo() {
	}

	public void removerArquivo(Arquivo arquivo) {
		arquivos.remove(arquivo);
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

	public UploadedFile getArquivo() {
		return arquivo;
	}

	public void setArquivo(UploadedFile arquivo) {
		this.arquivo = arquivo;
	}

	public List<UploadedFile> getArquivos() {
		return arquivos;
	}

	public void setArquivos(List<UploadedFile> arquivos) {
		this.arquivos = arquivos;
	}

}
