package com.unitedvision.sangihe.monev.exception;

/**
 * Exception yang spesifik untuk error anggaran.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
public class AnggaranException extends ApplicationException {

	private static final long serialVersionUID = 1L;

	public AnggaranException() {
		super();
	}

	/**
	 * Buat exception dengan pesan tertentu.
	 * @param arg0 pesan yang akan ditampilkan.
	 */
	public AnggaranException(String arg0) {
		super(arg0);
	}
}
