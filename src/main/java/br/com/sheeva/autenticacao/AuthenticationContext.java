package br.com.sheeva.autenticacao;

import javax.faces.bean.ManagedBean;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.sheeva.dominio.Usuario;
import br.com.sheeva.enu.Perfil;

@ManagedBean(name="authenticationContext")
@Service("authenticationContext")
public class AuthenticationContext {
	
	public Usuario getUsuarioLogado(){
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		if(authentication == null){
			return null;
		}
		return (Usuario) authentication.getPrincipal();
	}
	
	public boolean getPossuiPerfilAdministrador(){
		if(getUsuarioLogado()!=null){
			return getUsuarioLogado().hasPerfil(Perfil.PERFIL_ADMINISTRADOR);
		}
		return false;
	}
	
}

