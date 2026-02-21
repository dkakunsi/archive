package edu.dkakunsi.ta.presensi.exception;

public class GeneralException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * exception message untuk masalah umum yang tidak spesifik
	 */
	public static final String generalProblem = "Masalah umum";
	
	public GeneralException() {
		super();
	}
	
	public GeneralException(String message) {
		super(message);
	}

}
