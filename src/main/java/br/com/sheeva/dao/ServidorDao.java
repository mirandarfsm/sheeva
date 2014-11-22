package br.com.sheeva.dao;

import java.util.List;
import br.com.sheeva.dominio.Servidor;


public interface ServidorDao {

	public void save(Servidor servidor);

	public void remove(Integer id);

	public List<Servidor> getAll();

	public Servidor getById(Integer id);
	
	public Servidor getByNome(String nome);
	
	public Servidor getByInstanceName(String login);

}
