package br.com.sheeva.bean;

import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.sheeva.dominio.Usuario;
import br.com.sheeva.enu.EnumSelectItemsCreator;
import br.com.sheeva.enu.Perfil;
import br.com.sheeva.service.UsuarioService;
import br.com.sheeva.utils.LayoutIndexManager;
import br.com.sheeva.utils.Mensagem;

@Service("usuarioBean")
@Scope("session")
public class UsuarioBean {
	
	private Usuario usuario;
	private List<Usuario> usuarios;
	
	@Autowired
	private UsuarioService usuarioService;
	
	public UsuarioBean(){
		
	}
	
	public String abrirPagina(){
		usuarios = usuarioService.listarTodos();
		LayoutIndexManager.atualizarIndice(1);
		return "/pages/usuario/cadastrar-usuario-lista.xhtml";
	}
	
	public String novo(){
		usuario = new Usuario();
		usuario.getPerfis().add(Perfil.PERFIL_CONVENCIONAL);
		return "/pages/usuario/cadastrar-usuario-formulario.xhtml";
	}

	public String salvar() {
		usuarioService.salvar(usuario);
		if(usuario.getId()==null){
			Mensagem.msgInformacao("Usuario salvo com sucesso");
		}else {
			Mensagem.msgInformacao("Usuario alterado com sucesso");
		}
		return abrirPagina();
	}

	public void excluir() {
		usuarioService.remover(usuario.getId());
		usuarios = usuarioService.listarTodos();
		Mensagem.msgInformacao("Usuario excluído com sucesso");
	}

	public String editar() {
		usuario = usuarioService.buscarPeloId(usuario.getId());
		return "/pages/usuario/cadastrar-usuario-formulario.xhtml";
	}

	public String cancelar() {
		return "/pages/usuario/cadastrar-usuario-lista.xhtml";
	}

	public SelectItem[] getListaPerfis() {
		return EnumSelectItemsCreator.items(Perfil.class, "nome");
	}
	
	public Perfil getPerfilConvencional() {
		return Perfil.PERFIL_CONVENCIONAL;
	}

	public Perfil getPerfilAdministrador() {
		return Perfil.PERFIL_ADMINISTRADOR;
	}
	
	public void resetarSenha(){
		usuarioService.resetSenha(usuario);
		Mensagem.msgInformacao("Senha alterada com sucesso");
		Mensagem.msgInformacao("A Senha nova senha é igual ao login");
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
}