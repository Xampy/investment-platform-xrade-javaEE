package com.xrade.exception.resource;

import com.xrade.exception.DefaultResourceException;
import com.xrade.exception.ResourceExceptionType;

public class MissedBearerTokenException extends DefaultResourceException {
	
	public MissedBearerTokenException(String msg) {
		super(msg);
		this.type = ResourceExceptionType.MISSED_BEARER_TOKEN;
	}

}
