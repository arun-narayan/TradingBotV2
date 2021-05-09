package com.getbux.app.v2.exceptions;

public class BotException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2003837211652447920L;
	
	public BotException(String message) {
        super(message);
    }
	
	public BotException(String message, Throwable cause) {
        super(message, cause);
    }

}
