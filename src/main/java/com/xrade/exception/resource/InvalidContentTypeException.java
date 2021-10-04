package com.xrade.exception.resource;

import com.xrade.exception.DefaultResourceException;
import com.xrade.exception.ResourceExceptionType;

public class InvalidContentTypeException extends DefaultResourceException {
	
	public InvalidContentTypeException(String msg) {
		super(msg);
		this.type = ResourceExceptionType.INVALID_CONTENT_TYPE;
	}

}
