package com.unitedvision.sangihe.monev.exception;

/**
 * Error yang spesifik ketika data yang dicari tidak ditemukan pada database.<br />
 * Bisa digunakan sebagai pengganti dari PersistenceExecption.
 * @author Acer One 10
 *
 */
public class EntityNotExistsException extends ApplicationException {

	private static final long serialVersionUID = 1L;

	public EntityNotExistsException() {
		super();
	}

	/**
	 * Buat exception dengan pesan tertentu.
	 * @param arg0 pesan yang akan ditampilkan.
	 */
	public EntityNotExistsException(String arg0) {
		super(arg0);
	}

	/**
	 * Buat dari exception.
	 * @param exception penyebab error yang sebenarnya.
	 */
	public EntityNotExistsException(Exception exception) {
		super(exception);
	}
}
