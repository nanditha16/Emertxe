package com.example.snakeAndLadder.Exceptions;

public class InitialSetupException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String message;
   
    
    public InitialSetupException(String message) { 
    	this.message = message; 
    }

    @Override
    public String getMessage() {
    	return message;
    }
}
