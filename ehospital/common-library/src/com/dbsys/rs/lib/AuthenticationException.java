package com.dbsys.rs.lib;

/**
 * Exception jika terjadi kesalahan dalam proses otentikasi.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
public class AuthenticationException extends ApplicationException {

	private static final long serialVersionUID = 1L;

	public AuthenticationException() {
		super();
	}

	public AuthenticationException(String message) {
		super(message);
	}
}
