package br.com.sheeva.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.sheeva.dominio.Usuario;
import br.com.sheeva.enu.EnumSelectItemsCreator;
import br.com.sheeva.enu.Perfil;
import br.com.sheeva.service.UsuarioService;
import br.com.sheeva.utils.LayoutIndexManager;
import br.com.sheeva.utils.ManagedBeanUtils;
import br.com.sheeva.utils.Mensagem;

@Service("usuarioFormularioBean")
@Scope(value = "view")
public class UsuarioFormularioBean {
	
	private Usuario usuario;
	
	@Autowired
	private UsuarioService usuarioService;
	
	public UsuarioFormularioBean(){
		
	}
	
	@PostConstruct
	public void init(){
		usuario = obterUsuario();
		usuario.getPerfis().add(Perfil.PERFIL_CONVENCIONAL);
	}
	
	public Usuario obterUsuario() {
		String id = ManagedBeanUtils.obterParametroRequest("id");
		return "novo".equals(id) ? new Usuario() : usuarioService.buscarPeloId(Integer.valueOf(id));
//		return "novo".equals(id) ? new Usuario() : usuarioService.buscarPeloId(Integer.valueOf(id));
//		usuario = usuarioService.buscarPeloId(usuario.getId());
//		return "/pages/usuario/cadastrar-usuario-formulario.xhtml";
	}
	
	public void salvar() {
		if (usuarioService.isLoginEmUso(usuario)) {
			Mensagem.msgErro("Já existe uma pessoa cadastrada com este login.");
			return;
		}
		if (usuarioService.isEmailEmUso(usuario)) {
			Mensagem.msgErro("Já existe uma pessoa cadastrada com este e-mail.");
			return;
		}
		usuarioService.salvar(usuario);
		if(usuario.getId()==null){
			Mensagem.msgInformacao("Usuario salvo com sucesso");
		}else {
			Mensagem.msgInformacao("Usuario alterado com sucesso");
		}
		ManagedBeanUtils.redirecionar("/usuario");
	}

	public void cancelar() {
		ManagedBeanUtils.redirecionar("/usuario");
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
		Mensagem.msgInformacao("A nova senha é igual ao login");
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
