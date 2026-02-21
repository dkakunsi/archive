package com.dbsys.rs.security;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private CustomUserDetailsService userDetailService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
	    String username = authentication.getName();
        String password = (String) authentication.getCredentials();

       	CustomUser user;
		try {
			user = userDetailService.loadUserByUsername(password);
		} catch (PersistenceException e) {
            throw new BadCredentialsException(e.getMessage());
		}

       	if (!verifikasiPrincipal(user, username, password))
            throw new BadCredentialsException("Kombinasi Username dan Password Salah");
        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
	}

	/**
	 * Cek apakah username dan password yang di-load sama dengan username dan passwrd yang diinput user.
	 * @param user
	 * @param username
	 * @param password
	 * @return true jika keduanya sama, selain itu tidak.
	 */
	private boolean verifikasiPrincipal(CustomUser user, String username, String password) {
       	if (username.equals(user.getUsername()) && password.equals(user.getPassword()))
       		return true;
       	return false;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}
	
}
