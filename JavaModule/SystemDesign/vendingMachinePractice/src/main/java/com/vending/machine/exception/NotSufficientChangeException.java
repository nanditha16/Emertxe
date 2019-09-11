package com.vending.machine.exception;

public class NotSufficientChangeException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3709045304169872115L;
	private String message;
	
	public NotSufficientChangeException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
}
