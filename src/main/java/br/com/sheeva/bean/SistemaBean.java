package br.com.sheeva.bean;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.sheeva.dominio.Sistema;
import br.com.sheeva.service.SistemaService;
import br.com.sheeva.utils.LayoutIndexManager;
import br.com.sheeva.utils.ManagedBeanUtils;
import br.com.sheeva.utils.Mensagem;

@Service("sistemaBean")
@Scope(value = "session")
public class SistemaBean {

	private Sistema sistema;
	private List<Sistema> sistemas;

	@Autowired
	private SistemaService sistemaService;

	@PostConstruct
	public void init() {
		sistemas = sistemaService.listarTodos();
		LayoutIndexManager.atualizarIndice(1);
	}

	public String novo() {
		sistema = new Sistema();
		return "/pages/sistema/cadastrar-sistema-formulario.xhtml";
	}

	public void salvar() {
		sistemaService.salvar(sistema);
		if (sistema.getId() == null) {
			Mensagem.msgInformacao("Sistema salvo com sucesso");
		} else {
			Mensagem.msgInformacao("Sistema alterado com sucesso");
		}
		sistemas = sistemaService.listarTodos();
		ManagedBeanUtils.redirecionar("/sistema");
	}

	public void excluir(Sistema sistema) {
		sistemaService.remover(sistema.getId());
		sistemas = sistemaService.listarTodos();
		Mensagem.msgInformacao("Sistema excluído com sucesso");
	}

	public String editar() {
		sistema = sistemaService.buscarPeloId(sistema.getId());
		return "/pages/sistema/cadastrar-sistema-formulario.xhtml";
	}

	public void cancelar() {
		ManagedBeanUtils.redirecionar("/sistema");
	}

	public Sistema getSistema() {
		return sistema;
	}

	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}

	public List<Sistema> getSistemas() {
		return sistemas;
	}

	public void setSistemas(List<Sistema> sistemas) {
		this.sistemas = sistemas;
	}

}
