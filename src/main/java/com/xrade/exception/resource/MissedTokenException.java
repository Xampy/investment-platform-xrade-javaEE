package com.xrade.exception.resource;

import com.xrade.exception.DefaultResourceException;
import com.xrade.exception.ResourceExceptionType;

public class MissedTokenException extends DefaultResourceException {
	
	public MissedTokenException(String msg) {
		super(msg);
		this.type = ResourceExceptionType.MISSED_TOKEN;
	}
}
