package com.xrade.security.guard;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * An abstract Http guard
 * 
 * @author Software
 *
 */
public abstract class HttpRequestGuard {
	public HttpRequestGuard(ServletContext context, HttpServletRequest request,
			HttpServletResponse response, final HttpRequestGrantedStateListener listener){
		
	}
}
