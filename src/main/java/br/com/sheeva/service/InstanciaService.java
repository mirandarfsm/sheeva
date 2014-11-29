package br.com.sheeva.service;

import java.util.List;

import br.com.sheeva.dominio.Instancia;

public interface InstanciaService {
	public void salvar(Instancia instancia);

	public void remover(Integer id);

	public List<Instancia> listarTodos();

	public Instancia buscarPeloId(Integer id);

	public Instancia buscarPeloNome(String nome);
}
