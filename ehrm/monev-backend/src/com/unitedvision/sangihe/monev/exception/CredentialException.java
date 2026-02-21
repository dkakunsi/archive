package com.unitedvision.sangihe.monev.exception;

/**
 * Exception yang spesifik pada credential.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
public class CredentialException extends ApplicationException {

	private static final long serialVersionUID = 1L;

	public CredentialException() {
		super();
	}

	/**
	 * Buat baru dengan pesan tertentu.
	 * @param arg0 pesan yang akan ditampilkan.
	 */
	public CredentialException(String arg0) {
		super(arg0);
	}
}
