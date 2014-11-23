package br.com.sheeva.dao;

import java.util.List;

import br.com.sheeva.dominio.ServidorGrupo;

public interface ServidorGrupoDao {

	public void save(ServidorGrupo servidorGrupo);

	public void remove(Integer id);

	public List<ServidorGrupo> getAll();

	public ServidorGrupo getById(Integer id);

	public ServidorGrupo getByNome(String nome);
}
