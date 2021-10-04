package com.xrade.exception.resource;

import com.xrade.exception.DefaultResourceException;
import com.xrade.exception.ResourceExceptionType;

public class TokenVerificationErrorException extends DefaultResourceException {
	
	public TokenVerificationErrorException(String msg) {
		super(msg);
		this.type = ResourceExceptionType.TOKEN_VERIFICATION_ERROR;
	}

}
