package edu.dkakunsi.ta.presensi.exception;

public class PegawaiNotPresentException extends GeneralException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * exception message apabila pegawai tidak masuk
	 */
	public static final String notPresent = "Tidak hadir";

	/**
	 * Konstruksi PegawaiNotPresentException
	 */
	public PegawaiNotPresentException() {
		super();
	}
	
	/**
	 * Konstruksi PegawaiNotPresentException dengan pesan parameter
	 * @param message exception message diambil dari static member PegawaiNotPresentException
	 */
	public PegawaiNotPresentException(String message) {
		super(message);
	}

}
