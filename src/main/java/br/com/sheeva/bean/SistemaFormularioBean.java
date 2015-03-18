package br.com.sheeva.bean;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.sheeva.dominio.Sistema;
import br.com.sheeva.service.SistemaService;
import br.com.sheeva.utils.ManagedBeanUtils;
import br.com.sheeva.utils.Mensagem;

@Service("sistemaFormularioBean")
@Scope(value = "view")
public class SistemaFormularioBean {

	private Sistema sistema;

	@Autowired
	private SistemaService sistemaService;

	@PostConstruct
	public void init() {
		sistema = obterSistema();
	}

	public Sistema obterSistema() {
		String id = ManagedBeanUtils.obterParametroRequest("id");
		return "novo".equals(id) ? new Sistema() : sistemaService.buscarPeloId(Integer.valueOf(id));
	}

	public void salvar() {
		sistemaService.salvar(sistema);
		if (sistema.getId() == null) {
			Mensagem.msgInformacao("Sistema salvo com sucesso");
		} else {
			Mensagem.msgInformacao("Sistema alterado com sucesso");
		}
		ManagedBeanUtils.redirecionar("/sistema");
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

}
