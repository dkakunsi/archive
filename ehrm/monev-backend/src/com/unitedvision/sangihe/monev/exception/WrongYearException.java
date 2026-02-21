package com.unitedvision.sangihe.monev.exception;

/**
 * Exception yang spesifik ketika tahun yang diberikan berbeda dengan tahun yang diminta.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
public class WrongYearException extends ApplicationException {

	private static final long serialVersionUID = 1L;

	public WrongYearException() {
		super();
	}

	/**
	 * Buat baru dengan pesan tertentu.
	 * @param arg0 pesan yang akan disampaikan.
	 */
	public WrongYearException(String arg0) {
		super(arg0);
	}
}
