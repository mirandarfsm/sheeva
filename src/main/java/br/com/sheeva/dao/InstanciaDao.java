package br.com.sheeva.dao;

import java.util.List;

import br.com.sheeva.dominio.Instancia;

public interface InstanciaDao {
	public void save(Instancia instancia);

	public void remove(Integer id);

	public List<Instancia> getAll();

	public Instancia getById(Integer id);

	public Instancia getByNome(String nome);

}
