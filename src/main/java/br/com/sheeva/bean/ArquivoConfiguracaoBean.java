package br.com.sheeva.bean;

import java.io.IOException;
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

import com.jcraft.jsch.JSchException;

@Scope(value = "view")
@Service("arquivoConfiguracaoBean")
public class ArquivoConfiguracaoBean {

	private String configuracao;
	private Instancia instancia;
	private Servidor servidor;
	private List<Servidor> servidores;

	@Autowired
	private InstanciaService instanciaService;

	@Autowired
	private ServidorService servidorService;

	@PostConstruct
	public void init() throws IOException, JSchException {
		servidores = servidorService.listarTodos();
		servidor = servidores.get(0);
		instancia = obterInstancia();
		configuracao = instanciaService.getArquivoConfiguracao(servidor,
				instancia).replaceAll("\n", "<br>");
	}

	private Instancia obterInstancia() {
		String id = ManagedBeanUtils.obterParametroRequest("id");
		return instanciaService.buscarPeloId(Integer.valueOf(id));
	}

	public void salvar() throws IOException, JSchException {
		instanciaService.setArquivoConfiguracao(servidor, instancia,
				configuracao.replaceAll("<br>", "\n"));
		Mensagem.msgInformacao("Arquivo Configuracao alterado com sucesso");
		ManagedBeanUtils.redirecionar("/instancia");
	}

	public void submit() throws IOException, JSchException {
		instancia = obterInstancia();
		configuracao = instanciaService.getArquivoConfiguracao(servidor,
				instancia).replaceAll("\n", "<br>");
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
