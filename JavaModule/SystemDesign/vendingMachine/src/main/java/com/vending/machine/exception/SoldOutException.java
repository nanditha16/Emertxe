package com.vending.machine.exception;

public class SoldOutException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5770503902522382825L;
	
	private String message;
	
	public SoldOutException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
