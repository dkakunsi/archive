package com.dbsys.rs.lib;

/**
 * Representasi error ketika request untuk <i>secured resource</i> tidak dapat diotentikasi.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
public class UnauthenticatedAccessException extends ApplicationException {

	private static final long serialVersionUID = 1L;

	public UnauthenticatedAccessException() {
		super();
	}

	public UnauthenticatedAccessException(String message) {
		super(message);
	}
}
