package com.xrade.exception.resource;

import com.xrade.exception.DefaultResourceException;
import com.xrade.exception.ResourceExceptionType;

public class InvalidBodyFormatException extends DefaultResourceException {
	
	public InvalidBodyFormatException(String msg) {
		super(msg);
		this.type = ResourceExceptionType.INVALID_BODY_FORMAT;
	}

}
