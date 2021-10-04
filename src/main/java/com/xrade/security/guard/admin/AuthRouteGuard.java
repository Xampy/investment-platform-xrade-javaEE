package com.xrade.security.guard.admin;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xrade.entity.BackOfficerEntity;
import com.xrade.exception.security.ManagerNotAllowedToTraderException;
import com.xrade.exception.security.NoSessionException;
import com.xrade.exception.security.NoSessionUserException;
import com.xrade.exception.security.TraderNotAllowedToManagerException;
import com.xrade.security.guard.RouteGrantedStateListener;
import com.xrade.security.guard.RouteGuardInterface;

public class AuthRouteGuard implements RouteGuardInterface {
	
	/**
	 * Check if the user has access to asked
	 * ressource. It works on managers and traders routes
	 */
	public void canActivate(HttpServletRequest request, HttpServletResponse response) 
			throws NoSessionException, NoSessionUserException {	
		
		//Get the request session
		if(request.getSession(false) == null){			
			//The best redirect to login page
			throw new NoSessionException("No session");
		}else{
			//It seems that we have a session
			try{
				BackOfficerEntity user = (BackOfficerEntity) request.getSession(false).getAttribute("user");
				
				if(user == null){
					//Then we can only access to the login page
					throw new NoSessionUserException("No Session");
				}else{
					
					//Check the token
					//Check all fields in the back Officer entity object
					
					
				}
			}catch(Exception e){
				//Unable to cast the sessionuser to BackOfficerEntity
				throw new NoSessionUserException("No User Exception");
			}
			
		}
		
		
		
		
		
	}
	

	public AuthRouteGuard(ServletContext context, HttpServletRequest request,
			HttpServletResponse response, RouteGrantedStateListener listener) throws ServletException, IOException {
		super();
		
		try {
			this.canActivate(request, response);
			
			//After all check
			if(listener != null)
				listener.onGranted(context, request, response);
			
		} catch (NoSessionException e) {
			e.printStackTrace();
			context.getRequestDispatcher("/auth/login.html.jsp").forward(request, response);
		} catch (NoSessionUserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			context.getRequestDispatcher("/auth/login.html.jsp").forward(request, response);
		}
		
		
		
		
	}

}