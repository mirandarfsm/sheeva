package br.com.sheeva.bean;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.sheeva.dominio.Servidor;
import br.com.sheeva.service.ServidorService;
import br.com.sheeva.utils.LayoutIndexManager;
import br.com.sheeva.utils.ManagedBeanUtils;
import br.com.sheeva.utils.Mensagem;


@Service("servidorBean")
@Scope(value = "session")
public class ServidorBean {

	private Servidor servidor;
	private List<Servidor> servidores;
	
	@Autowired
	private ServidorService servidorService;
	
	public ServidorBean() {
	
	}
	
	@PostConstruct
	public void init(){
		servidores = servidorService.listarTodos();
		LayoutIndexManager.atualizarIndice(1);
	}
	
	public String novo(){
		servidor = new Servidor();
		return "/pages/servidor/cadastrar-servidor-formulario.xhtml";
	}

	public void salvar() {
		servidorService.salvar(servidor);
		if(servidor.getId() == null){
			Mensagem.msgInformacao("Servidor salvo com sucesso");
		}else {
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

	public String cancelar() {
		return "/pages/servidor/cadastrar-servidor-lista.xhtml";
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
	
	
}
