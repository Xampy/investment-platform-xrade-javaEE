package com.xrade.exception;

public class DefaultResourceException extends Exception{
	/**
	 * Type of exception about resource
	 */
	public ResourceExceptionType type = ResourceExceptionType.DEFAULT;
	
	public DefaultResourceException(String message) {
		super(message);
	}
}
