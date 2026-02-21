package edu.dkakunsi.ta.presensi.exception;

public class PegawaiNotExistException extends GeneralException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Konstruksi PegawaiNotExistException
	 */
	public PegawaiNotExistException() {
		super();
	}
	
	/**
	 * Konstruksi PegawaiNotExistException dengan pesan parameter
	 * @param message exception message diambil dari static member PegawaiNotExistException
	 */
	public PegawaiNotExistException(String message) {
		super(message);
	}

}
