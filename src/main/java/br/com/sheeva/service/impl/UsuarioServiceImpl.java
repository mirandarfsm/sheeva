package br.com.sheeva.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.sheeva.dominio.Usuario;
import br.com.sheeva.service.UsuarioService;
import br.com.sheeva.usuarioDao.UsuarioDao;

@Service("usuarioService")
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	UsuarioDao usuarioDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public void salvar(Usuario usuario) {
		if (usuario.getId() == null) {
			usuario.setSenha(passwordEncoder.encodePassword(usuario.getLogin(),null));
		}
		usuarioDao.save(usuario);

	}

	public void remover(Integer id) {
		usuarioDao.remove(id);
	}

	public List<Usuario> listarTodos() {
		return usuarioDao.getAll();
	}

	public Usuario buscarPeloId(Integer id) {
		return usuarioDao.getById(id);
	}

	public Usuario buscarPeloLogin(String login) {
		return usuarioDao.getByLogin(login);
	}
	
	public void mudarSenha(String login, String senhaAtual, String novaSenha) {
		Usuario usuario = buscarPeloLogin(login);
		if (!passwordEncoder.encodePassword(senhaAtual, null).equals(usuario.getSenha())) {
			throw new RuntimeException("A senha atual inserida está incorreta!");
		}
		usuario.setSenha(passwordEncoder.encodePassword(novaSenha, null));
		usuarioDao.save(usuario);
	}

	public void resetSenha(Usuario usuario) {
		usuario.setSenha(passwordEncoder.encodePassword(usuario.getLogin(), null));
		usuarioDao.save(usuario);
	}
	
	public boolean isLoginEmUso(Usuario usuario) {
		Usuario usuarioExistente = usuarioDao.getByLogin(usuario.getLogin());
		if (usuarioExistente != null && !usuarioExistente.equals(usuario)) {
			return true;
		}
		return false;
	}

	public boolean isEmailEmUso(Usuario usuario) {
		Usuario usuarioExistente = usuarioDao.getByEmail(usuario.getEmail());
		if (usuarioExistente != null && !usuarioExistente.equals(usuario)) {
			return true;
		}
		return false;
	}


}
