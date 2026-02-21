package edu.dkakunsi.ta.presensi.exception;

public class ClientNotAuthenticatedException extends GeneralException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * exception message jika client yang dicari tidak ada
	 */
	public static final String clientNotFound = "Tidak ada client";
	
	/**
	 * Konstruksi ClientNotAuthenticatedException
	 */
	public ClientNotAuthenticatedException() {
		super();
	}

	/**
	 * Konstruksi ClientNotAuthenticatedException dengan pesan parameter
	 * @param message exception message diambil dari static member ClientNotAuthenticatedException
	 */
	public ClientNotAuthenticatedException(String message) {
		super(message);
	}
}
