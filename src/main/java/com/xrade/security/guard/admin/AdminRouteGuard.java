package com.xrade.security.guard.admin;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xrade.entity.BackOfficerEntity;
import com.xrade.exception.DefaultResourceException;
import com.xrade.exception.DefaultSecurityException;
import com.xrade.exception.security.ManagerNotAllowedToTraderException;
import com.xrade.exception.security.NoSessionException;
import com.xrade.exception.security.NoSessionTokenException;
import com.xrade.exception.security.NoSessionUserException;
import com.xrade.exception.security.TraderNotAllowedToManagerException;
import com.xrade.security.guard.RouteGrantedStateListener;
import com.xrade.security.guard.RouteGuardInterface;

public class AdminRouteGuard implements RouteGuardInterface {
	
	/**
	 * Check if the user has access to asked
	 * ressource. It works on managers and traders routes.
	 * 
	 * The main purpose is to check the access token
	 * 
	 */
	public void canActivate(HttpServletRequest request, HttpServletResponse response) 
			throws NoSessionTokenException {
		
		//Check the token
		try{
			String token = (String) request.getSession(false).getAttribute("token");
			if(token == null || token.trim().equals("")){
				throw new NoSessionTokenException("Not allowed");
			}
		}catch(Exception e){
			throw new NoSessionTokenException("Not allowed");
		}
		
		/*String url = request.getRequestURI();
		System.out.println("Requested uri " + url);
		
		if(user.getLevel() == "trader"){
			
			if(url.startsWith("/trader")){
				
			}else{
				//The resource is Not allowed
				throw new TraderNotAllowedToManagerException("Not allowed");
			}
			
		}else if(user.getLevel() == "manager"){
			
			if(url.startsWith("/manager")){
				
			}else{
				//The ressource is not allowed
				throw new ManagerNotAllowedToTraderException("Not allowed");
			}
			
		}*/
		
	}
	

	public AdminRouteGuard(ServletContext context, HttpServletRequest request,
			HttpServletResponse response, final RouteGrantedStateListener listener) throws ServletException, IOException {
		super();
		
		
		
		
		//Are authorized authenticated user
		new AuthRouteGuard(context, request, response,
				new RouteGrantedStateListener(){
					
					public void onGranted(ServletContext context, HttpServletRequest request,
							HttpServletResponse response) throws ServletException, IOException{
						
						
						try {
							AdminRouteGuard.this.canActivate(request, response);
							if(listener != null)
								listener.onGranted(context, request, response);
						} catch (NoSessionTokenException e) {
							e.printStackTrace();
							if(listener != null)
								listener.onDenied(e, context, request, response);
						}
						
						
						
					}
					
					public void onDenied(DefaultSecurityException e, ServletContext context, HttpServletRequest request,
							HttpServletResponse response) throws ServletException, IOException {
						return;
						
					}
				}
		);
		
		
		
		
		
		
		
	}

}