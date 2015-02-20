package br.com.sheeva.service;

import java.util.List;

import br.com.sheeva.dominio.Sistema;

public interface SistemaService {

	public void salvar(Sistema sistema);

	public void remover(Integer id);

	public List<Sistema> listarTodos();

	public Sistema buscarPeloId(Integer id);

	public Sistema buscarPeloNome(String nome);

}
