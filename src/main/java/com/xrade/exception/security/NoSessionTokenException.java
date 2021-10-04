package com.xrade.exception.security;

import com.xrade.exception.DefaultSecurityException;
import com.xrade.exception.SecurityExceptionType;

public class NoSessionTokenException extends DefaultSecurityException {

	public NoSessionTokenException(String message) {
		super(message);
		this.type = SecurityExceptionType.NO_SESSION_TOKEN;
	}

}
