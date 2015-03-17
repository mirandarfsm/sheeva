package br.com.sheeva.bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

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
	private UploadedFile arquivoAplicacao;
	private UploadedFile arquivoBancoDados;
	private String arquivoAplicacaoParaRemover;
	private String arquivoBancoDadosParaRemover;
	private InputStream arquivoAplicacaoInputStream;
	private InputStream arquivoBancoDadosInputStream;
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

	public void salvar() throws IOException {
		versaoService.salvar(versao);
		if (arquivoAplicacao != null) {
			salvarArquivosUploadNoDisco(arquivoAplicacaoInputStream, versao.getArquivoAplicacao(), arquivoAplicacaoParaRemover);
			arquivoAplicacaoParaRemover = null;
			arquivoAplicacao = null;
		}
		if (arquivoBancoDados != null) {
			salvarArquivosUploadNoDisco(arquivoBancoDadosInputStream, versao.getArquivoBancoDados(), arquivoBancoDadosParaRemover);
			arquivoBancoDadosParaRemover = null;
			arquivoBancoDados = null;
		}
		if (versao.getId() == null) {
			Mensagem.msgInformacao("Versao salvo com sucesso");
		} else {
			Mensagem.msgInformacao("Versao alterado com sucesso");
		}
		versoes = versaoService.listarTodos();
		ManagedBeanUtils.redirecionar("/versao");
	}

	public void excluir(Versao versao) {
		versaoService.remover(versao.getId());
		versaoService.deletarDiretorio(versao.getFolder());
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

	public void realizarUploadArquivoAplicacao(FileUploadEvent event) {
		if (arquivoAplicacaoParaRemover == null && versao.getArquivoAplicacao() != null) {
			arquivoAplicacaoParaRemover = versao.getArquivoAplicacao();
		}
		arquivoAplicacao  = event.getFile();
		try {
			arquivoAplicacaoInputStream = arquivoAplicacao.getInputstream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		versao.setArquivoAplicacao(arquivoAplicacao.getFileName());
	}
	
	public void realizarUploadArquivoBanco(FileUploadEvent event) {
		if (arquivoBancoDadosParaRemover == null && versao.getArquivoBancoDados() != null) {
			arquivoBancoDadosParaRemover = versao.getArquivoBancoDados();
		}
		arquivoBancoDados  = event.getFile();
		try {
			arquivoBancoDadosInputStream = arquivoBancoDados.getInputstream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		versao.setArquivoBancoDados(arquivoBancoDados.getFileName());
	}
	
	private void salvarArquivosUploadNoDisco(InputStream arquivo, String nomeArquivo, String arquivoParaRemover) throws IOException {
		versaoService.deletarArquivo(versao.getFolder() + arquivoParaRemover);
		versaoService.salvarArquivo(versao, arquivo, nomeArquivo);
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

	public List<UploadedFile> getArquivos() {
		return arquivos;
	}

	public void setArquivos(List<UploadedFile> arquivos) {
		this.arquivos = arquivos;
	}

	public UploadedFile getArquivoAplicacao() {
		return arquivoAplicacao;
	}

	public void setArquivoAplicacao(UploadedFile arquivoAplicacao) {
		this.arquivoAplicacao = arquivoAplicacao;
	}

	public UploadedFile getArquivoBancoDados() {
		return arquivoBancoDados;
	}

	public void setArquivoBancoDados(UploadedFile arquivoBancoDados) {
		this.arquivoBancoDados = arquivoBancoDados;
	}

}
