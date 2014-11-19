package br.com.sheeva.service;

import java.util.List;

import br.com.sheeva.dominio.Usuario;

public interface UsuarioService {

	public void salvar(Usuario usuario);
	
	public void remover(Integer id);
	
	public List<Usuario> listarTodos();
	
	public Usuario buscarPeloId(Integer id);

	public Usuario buscarPeloLogin(String login);

	void mudarSenha(String login, String senhaAtual, String novaSenha);

	public void resetSenha(Usuario usuario);
	
}
