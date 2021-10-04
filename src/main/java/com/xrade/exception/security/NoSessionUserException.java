package com.xrade.exception.security;

import com.xrade.exception.DefaultSecurityException;
import com.xrade.exception.SecurityExceptionType;

public class NoSessionUserException extends DefaultSecurityException {

	public NoSessionUserException(String message) {
		super(message);
		this.type = SecurityExceptionType.NO_SESSION_USER;
	}

}
