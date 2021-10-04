package com.xrade.exception.security;

import com.xrade.exception.DefaultSecurityException;
import com.xrade.exception.SecurityExceptionType;

public class NoSessionException extends DefaultSecurityException {

	public NoSessionException(String msg) {
		super(msg);
		this.type = SecurityExceptionType.NO_SESSION;
	}
	
}
