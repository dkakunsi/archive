package com.dbsys.rs.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dbsys.rs.lib.OutOfDateEntityException;
import com.dbsys.rs.lib.UnauthenticatedAccessException;
import com.dbsys.rs.lib.entity.Operator;
import com.dbsys.rs.lib.entity.Operator.Role;
import com.dbsys.rs.lib.entity.Token;
import com.dbsys.rs.security.repository.TokenKeyRepository;

/**
 * Custom Authentication Provider.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
@Service("authService")
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private TokenKeyRepository tokenKeyRepository;

	@Override
	public CustomUser loadUserByUsername(String key) throws UsernameNotFoundException {
		try {
			Token token = getToken(key);
			Operator operator = token.getOperator();
			
			return new CustomUser(operator, key, getAuthorities(operator.getRole()));
		} catch (OutOfDateEntityException | UnauthenticatedAccessException e) {
			throw new UsernameNotFoundException("Username atau password salah");
		}
	}
	
	private Token getToken(String key) throws OutOfDateEntityException, UnauthenticatedAccessException {
		Token token = tokenKeyRepository.findOne(key);
		if (token.isExpire())
			throw new OutOfDateEntityException();
		if (token.isLock())
			throw new UnauthenticatedAccessException();
		if (!token.isExtensible())
			return token;
		token.extend(1);
		return tokenKeyRepository.save(token);
	}

	/**
	 * Mengambil role/authority operator.
	 * 
	 * @param role
	 * 
	 * @return daftar authority operator
	 */
	public static List<GrantedAuthority> getAuthorities(Role role) {
		List<GrantedAuthority> authList = new ArrayList<>();

		if (role.equals(Role.OPERATOR)) {
			authList.add(new SimpleGrantedAuthority("ROLE_OPERATOR"));
		} else if (role.equals(Role.ADMIN)) {
			authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}

		return authList;
	}
}
