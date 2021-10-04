package com.xrade.security.guard;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xrade.exception.DefaultResourceException;

public interface ResourceGrantedStateListener extends HttpRequestGrantedStateListener {
	void onGranted(ServletContext context, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException;
	void onDenied(DefaultResourceException e, ServletContext context, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

	
}
