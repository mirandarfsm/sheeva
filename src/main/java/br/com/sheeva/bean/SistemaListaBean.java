package br.com.sheeva.bean;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.sheeva.dominio.Sistema;
import br.com.sheeva.service.SistemaService;
import br.com.sheeva.utils.Mensagem;

@Service("sistemaListaBean")
@Scope(value = "view")
public class SistemaListaBean {

	private List<Sistema> sistemas;

	@Autowired
	private SistemaService sistemaService;

	@PostConstruct
	public void init() {
		sistemas = sistemaService.listarTodos();
	}

	public void excluir(Sistema sistema) {
		sistemaService.remover(sistema.getId());
		sistemas = sistemaService.listarTodos();
		Mensagem.msgInformacao("Sistema excluído com sucesso");
	}

	public List<Sistema> getSistemas() {
		return sistemas;
	}

	public void setSistemas(List<Sistema> sistemas) {
		this.sistemas = sistemas;
	}

}
