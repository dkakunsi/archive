package com.dbsys.rs.account.service;

import com.dbsys.rs.lib.UnauthenticatedAccessException;
import com.dbsys.rs.lib.entity.Token;

/**
 * Kelas untuk mengelola token.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
public interface TokenService {

	/**
	 * Membuat token jika username dan password valid.
	 * 
	 * @param username
	 * @param password
	 * 
	 * @return token
	 * 
	 * @throws UnauthenticatedAccessException username dan password tidak valid.
	 */
	Token create(String username, String password) throws UnauthenticatedAccessException;

	/**
	 * Mengunci token agar tidak dapat digunakan lagi. Dapat digunakan pada saat logout.
	 * 
	 * @param tokenString
	 */
	void lock(String tokenString);

}
