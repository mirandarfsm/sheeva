package br.com.sheeva.autenticacao;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.sheeva.service.UsuarioService;

public class SheevaAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UsuarioService usuarioService;

	public Authentication authenticate(Authentication authentication) {	

		try {
			UsernamePasswordAuthenticationToken userToken = (UsernamePasswordAuthenticationToken) authentication;
			String loginFornecido = userToken.getName().toLowerCase();
			String senhaFornecida = (String) userToken.getCredentials();
			if (loginFornecido == null || loginFornecido.trim().equals("")
					|| senhaFornecida == null
					|| senhaFornecida.trim().equals("")) {
				throw new BadCredentialsException("Falha na autenticação");
			}
			UserDetails details = usuarioService
					.buscarPeloLogin(loginFornecido);
			if (details == null) {
				throw new BadCredentialsException("Falha na autenticação");
			}
			if (!passwordEncoder.encodePassword(senhaFornecida, null).equals(
					details.getPassword())) {
				throw new BadCredentialsException("Falha na autenticação");
			}
			// Set<GrantedAuthority> authorities= new
			// HashSet<GrantedAuthority>();
			// authorities.add(new GrantedAuthorityImpl("ROLE_USER"));
			return new UsernamePasswordAuthenticationToken(details,
					senhaFornecida, details.getAuthorities());
		} catch (NoResultException e) {
			throw new BadCredentialsException("Falha na autenticação");
		}
	}

	@SuppressWarnings("rawtypes")
	public boolean supports(Class authentication) {
		return (UsernamePasswordAuthenticationToken.class
				.isAssignableFrom(authentication));
	}

}
