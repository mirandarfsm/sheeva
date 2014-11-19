package br.com.sheeva.usuarioDao;

import java.util.List;

import br.com.sheeva.dominio.Usuario;


public interface UsuarioDao {

	public void save(Usuario usuario);
	
	public void remove(Integer id);
	
	public List<Usuario> getAll();
	
	public Usuario getById(Integer id);
	
	public Usuario getByLogin(String login);

}
