package br.com.sheeva.autenticacao;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

public class SheevaAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public Authentication authenticate(Authentication authentication) {

		UsernamePasswordAuthenticationToken userToken = (UsernamePasswordAuthenticationToken) authentication;

		String loginFornecido = "teste";
		String senhaFornecida = "teste";
		UserDetails details = new UserDetails() {

			public boolean isEnabled() {
				// TODO Auto-generated method stub
				return false;
			}

			public boolean isCredentialsNonExpired() {
				// TODO Auto-generated method stub
				return false;
			}

			public boolean isAccountNonLocked() {
				// TODO Auto-generated method stub
				return false;
			}

			public boolean isAccountNonExpired() {
				// TODO Auto-generated method stub
				return false;
			}

			public String getUsername() {
				// TODO Auto-generated method stub
				return null;
			}

			public String getPassword() {
				// TODO Auto-generated method stub
				return null;
			}

			public Collection<GrantedAuthority> getAuthorities() {
				Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
				authorities.add(new GrantedAuthorityImpl("ROLE_USER"));
				return authorities;
			}
		};
		return new UsernamePasswordAuthenticationToken(details, senhaFornecida,
				details.getAuthorities());
	}

	@SuppressWarnings("rawtypes")
	public boolean supports(Class authentication) {
		return (UsernamePasswordAuthenticationToken.class
				.isAssignableFrom(authentication));
	}

}
