package com.dbsys.rs.lib;

/**
 * Representasi error ketika tanggal melewati batas yang ditentukan.
 * 
 * @author Deddy Christoper Kakunsi.
 *
 */
public class OutOfDateEntityException extends ApplicationException {

	private static final long serialVersionUID = 1L;

	public OutOfDateEntityException() {
		super();
	}

	public OutOfDateEntityException(String message) {
		super(message);
	}
}
