package com.vending.machine.exception;

public class NotFullPaidException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1050243138888838059L;
	private String message;
    private long remaining;
    
    public NotFullPaidException(String message, long remaining) { 
    	this.message = message; 
    	this.remaining = remaining; 
    }

    @Override
    public String getMessage() {
    	return message;
    }
    
    public String getRemaining() {
    	return message+ " " + remaining;
    }
    
}
