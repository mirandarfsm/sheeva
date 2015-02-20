package br.com.sheeva.dao;

import java.util.List;

import br.com.sheeva.dominio.Sistema;

public interface SistemaDao {

	public void save(Sistema sistema);

	public void remove(Integer id);

	public List<Sistema> getAll();

	public Sistema getById(Integer id);

	public Sistema getByNome(String nome);

}
