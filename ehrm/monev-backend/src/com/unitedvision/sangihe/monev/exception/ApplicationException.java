package com.unitedvision.sangihe.monev.exception;

/**
 * General exception untuk error yang dihasilkan oleh pemrosesan pada aplikasi.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
public class ApplicationException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public ApplicationException() {
		super();
	}

	/**
	 * Buat exception menggunakan pesan tertentu.
	 * @param arg0 pesan yang akan disampaikan.
	 */
	public ApplicationException(String arg0) {
		super(arg0);
	}
	
	/**
	 * Buat baru dari exception.
	 * @param exception penyebab error yang sebenarnya.
	 */
	public ApplicationException(Exception exception) {
		this(exception.getMessage());
		this.initCause(exception);
	}
}
