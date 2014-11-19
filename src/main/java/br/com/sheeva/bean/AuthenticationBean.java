package br.com.sheeva.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.sheeva.autenticacao.AuthenticationContext;
import br.com.sheeva.dominio.Usuario;

@Service("authenticationBean")
@Scope("session")
public class AuthenticationBean {

	@Autowired
	private AuthenticationContext authenticationContext;
	
	public AuthenticationBean(){

	}
	
	public Usuario getUsuarioLogado(){
		return authenticationContext.getUsuarioLogado();
	}
	
}
