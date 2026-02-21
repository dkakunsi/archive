package com.unitedvision.sangihe.monev.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.unitedvision.sangihe.monev.configuration.ApplicationConfig;
import com.unitedvision.sangihe.monev.entity.Operator;
import com.unitedvision.sangihe.monev.entity.Pegawai;
import com.unitedvision.sangihe.monev.entity.Token;
import com.unitedvision.sangihe.monev.entity.Operator.Role;
import com.unitedvision.sangihe.monev.exception.UnauthenticatedAccessException;
import com.unitedvision.sangihe.monev.service.TokenService;

/**
 * Custom Authentication Provider.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
@org.springframework.stereotype.Service("authService")
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private TokenService service;

	@Override
	public CustomUser loadUserByUsername(String tokenString) throws UsernameNotFoundException {
		Operator operator;
		Token token;

		try {
			token = service.get(tokenString);
			operator = getOperator(token.getpegawai());
		} catch (Exception e) {
			e.printStackTrace();
			throw new UsernameNotFoundException(e.getMessage());
		}
		
		return new CustomUser(operator.getUsername(), token.getToken(), operator, getAuthorities(operator.getRole()));
	}

	public CustomUser loadUserByUsername(String tokenString, String username) throws UsernameNotFoundException {
		Operator operator;
		Token token;

		try {
			token = service.get(tokenString);
			operator = getOperator(token.getpegawai());
		} catch (Exception e) {
			throw new UsernameNotFoundException(e.getMessage());
		}
		
		return new CustomUser(operator.getUsername(), token.getToken(), operator, getAuthorities(operator.getRole()));
	}

	public static List<GrantedAuthority> getAuthorities(Role role) {
		List<GrantedAuthority> authList = new ArrayList<>();

		if (role.equals(Role.ADMIN)) {
			authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			authList.add(new SimpleGrantedAuthority("ROLE_OPERATOR"));
		} else if (role.equals(Role.OPERATOR)) {
			authList.add(new SimpleGrantedAuthority("ROLE_OPERATOR"));
		}

		return authList;
	}
	
	/**
	 * Check daftar operator, apakah aplikasi Monitoring dan Evaluasi (MONEV) terdaftar. 
	 * Jika terdaftar, return object {@link Operator}. Jika tidak terdaftar throw {@link UnauthenticatedAccessException}
	 * @param pegawai object pegawai yang berhasil login.
	 * @return Object {@link Operator} untuk aplikasi MONEV.
	 * @throws UnauthenticatedAccessException tidak ada operator untuk aplikasi MONEV.
	 */
	private Operator getOperator(Pegawai pegawai) throws UnauthenticatedAccessException {
		List<Operator> daftarOperator = pegawai.getListOperator();

		for (Operator operator : daftarOperator) {
			if (operator.getKodeAplikasi().equals(ApplicationConfig.KODE_APLIKASI) || operator.getKodeAplikasi().equals("ALL"))
				return operator;
		}
		
		throw new UnauthenticatedAccessException("Aplikasi Kepegawaian tidak bisa anda akses");
	}
}
