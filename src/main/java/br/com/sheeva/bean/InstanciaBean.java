package br.com.sheeva.bean;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.sheeva.dominio.Instancia;
import br.com.sheeva.service.InstanciaService;
import br.com.sheeva.utils.LayoutIndexManager;
import br.com.sheeva.utils.ManagedBeanUtils;
import br.com.sheeva.utils.Mensagem;

@Service("instanciaBean")
@Scope(value = "session")
public class InstanciaBean {

	private Instancia instancia;
	private List<Instancia> instancias;

	@Autowired
	private InstanciaService instanciaService;

	public InstanciaBean() {

	}

	@PostConstruct
	public void init() {
		instancias = instanciaService.listarTodos();
		LayoutIndexManager.atualizarIndice(1);
	}

	public String novo() {
		instancia = new Instancia();
		return "/pages/instancia/cadastrar-instancia-formulario.xhtml";
	}

	public void salvar() {
		instanciaService.salvar(instancia);
		if (instancia.getId() == null) {
			Mensagem.msgInformacao("Instancia salvo com sucesso");
		} else {
			Mensagem.msgInformacao("Instancia alterado com sucesso");
		}
		instancias = instanciaService.listarTodos();
		ManagedBeanUtils.redirecionar("/instancia");
	}

	public void excluir() {
		instanciaService.remover(instancia.getId());
		instancias = instanciaService.listarTodos();
		Mensagem.msgInformacao("Instancia excluído com sucesso");
	}

	public String editar() {
		instancia = instanciaService.buscarPeloId(instancia.getId());
		return "/pages/instancia/cadastrar-instancia-formulario.xhtml";
	}

	public void cancelar() {
		ManagedBeanUtils.redirecionar("/instancia");
	}

	public Instancia getInstancia() {
		return instancia;
	}

	public void setInstancia(Instancia instancia) {
		this.instancia = instancia;
	}

	public List<Instancia> getInstancias() {
		return instancias;
	}

	public void setInstancias(List<Instancia> instancias) {
		this.instancias = instancias;
	}

}
