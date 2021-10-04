package com.xrade.exception;

public abstract class DefaultSecurityException extends Exception {
	
	/**
	 * Type of exception about security
	 */
	public SecurityExceptionType type = SecurityExceptionType.DEFAULT;
	
	public DefaultSecurityException(String message) {
		super(message);
	}
}
