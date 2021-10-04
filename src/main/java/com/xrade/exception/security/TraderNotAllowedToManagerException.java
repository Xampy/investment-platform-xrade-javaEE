package com.xrade.exception.security;

import com.xrade.exception.DefaultSecurityException;
import com.xrade.exception.SecurityExceptionType;

public class TraderNotAllowedToManagerException extends DefaultSecurityException {

	public TraderNotAllowedToManagerException(String message) {
		super(message);
		this.type = SecurityExceptionType.TRADER_NOT_ALLOWED_TO_MANAGER;
	}

}
