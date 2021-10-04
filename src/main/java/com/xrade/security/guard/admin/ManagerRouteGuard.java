package com.xrade.security.guard.admin;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xrade.entity.BackOfficerEntity;
import com.xrade.exception.DefaultSecurityException;
import com.xrade.exception.security.NoSessionTokenException;
import com.xrade.exception.security.TraderNotAllowedToManagerException;
import com.xrade.security.guard.RouteGrantedStateListener;
import com.xrade.security.guard.RouteGuardInterface;


public class ManagerRouteGuard implements RouteGuardInterface {


	/**
	 * Check if the user has access to asked
	 * ressource. It works on managers 
	 * 
	 * The main purpose is to check the resource asket
	 * 
	 */
	public void canActivate(HttpServletRequest request, HttpServletResponse response) 
			throws TraderNotAllowedToManagerException {
		
		BackOfficerEntity user = (BackOfficerEntity) request.getSession(false).getAttribute("user");
		String url = request.getRequestURI();
		System.out.println("Requested uri " + url);
		
		if(user.getLevel() == "trader"){
			
			if(url.startsWith("/manager")){
				//The resource is Not allowed
				throw new TraderNotAllowedToManagerException("Not allowed");
			}
			
		}
		
		/*else if(user.getLevel() == "manager"){
			
			if(url.startsWith("/manager")){
				
			}else{
				//The ressource is not allowed
				throw new ManagerNotAllowedToTraderException("Not allowed");
			}
			
		}*/
		
	}
	

	public ManagerRouteGuard(ServletContext context, HttpServletRequest request,
			HttpServletResponse response, final RouteGrantedStateListener listener) throws ServletException, IOException {
		super();
		
		
		
		
		//Are authorized authenticated user
		new AdminRouteGuard(context, request, response,
				new RouteGrantedStateListener(){
					
					public void onGranted(ServletContext context, HttpServletRequest request,
							HttpServletResponse response) throws ServletException, IOException{
						
						
						try {
							ManagerRouteGuard.this.canActivate(request, response);
							if(listener != null)
								listener.onGranted(context, request, response);
						} catch (TraderNotAllowedToManagerException e) {
							e.printStackTrace();
							if(listener != null)
								listener.onDenied(e, context, request, response);
						}
						
						
						
					}
					
					
					public void onDenied(DefaultSecurityException e, ServletContext context, HttpServletRequest request,
							HttpServletResponse response) throws ServletException, IOException {
						
						response.sendError(HttpServletResponse.SC_NON_AUTHORITATIVE_INFORMATION, "You are not authorized");
						
					}
				}
		);
	}
}