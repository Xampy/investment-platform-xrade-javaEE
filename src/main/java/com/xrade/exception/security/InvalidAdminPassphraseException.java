package com.xrade.exception.security;

import com.xrade.exception.DefaultResourceException;
import com.xrade.exception.DefaultSecurityException;
import com.xrade.exception.ResourceExceptionType;
import com.xrade.exception.SecurityExceptionType;

public class InvalidAdminPassphraseException extends DefaultResourceException{

	public InvalidAdminPassphraseException(String message) {
		super(message);
		this.type = ResourceExceptionType.NOT_ADMIN;
	}
	
	
}
