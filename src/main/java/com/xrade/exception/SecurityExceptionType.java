package com.xrade.exception;

public enum SecurityExceptionType {
	DEFAULT, 
	MANAGER_NOT_ALLOWED_TO_TRADER,
	NO_SESSION, NO_SESSION_USER,
	TRADER_NOT_ALLOWED_TO_MANAGER,
	NO_SESSION_TOKEN, NOT_ADMIN

}
