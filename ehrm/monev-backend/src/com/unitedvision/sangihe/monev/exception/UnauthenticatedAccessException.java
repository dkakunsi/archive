package com.unitedvision.sangihe.monev.exception;


public class UnauthenticatedAccessException extends ApplicationException {

	private static final long serialVersionUID = 1L;

	public UnauthenticatedAccessException() {
		super();
	}

	public UnauthenticatedAccessException(String message) {
		super(message);
	}

}
