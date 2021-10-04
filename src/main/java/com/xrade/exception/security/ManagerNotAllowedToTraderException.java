package com.xrade.exception.security;

import com.xrade.exception.DefaultSecurityException;
import com.xrade.exception.SecurityExceptionType;

public class ManagerNotAllowedToTraderException extends DefaultSecurityException {
	
	public ManagerNotAllowedToTraderException(String message) {
		super(message);
		this.type = SecurityExceptionType.MANAGER_NOT_ALLOWED_TO_TRADER;
	}
	
}
