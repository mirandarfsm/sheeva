package br.com.sheeva.dao;

import java.util.List;

import br.com.sheeva.dominio.Versao;

public interface VersaoDao {

	public void save(Versao versao);

	public void remove(Integer id);

	public List<Versao> getAll();

	public Versao getById(Integer id);
	
	public Versao getByVersion(String versao);
	
	public List<Versao> getVersionList(Versao antiga, Versao nova);
	
	public List<Versao> getVersionBySystemOrder(Integer idSistema);

}
