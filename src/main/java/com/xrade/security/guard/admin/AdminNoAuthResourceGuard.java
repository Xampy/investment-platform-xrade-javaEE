package com.xrade.security.guard.admin;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.xrade.exception.resource.InvalidBodyFormatException;
import com.xrade.exception.security.InvalidAdminPassphraseException;
import com.xrade.security.guard.HttpRequestGuard;
import com.xrade.security.guard.ResourceGrantedStateListener;
import com.xrade.security.guard.ResourceGuardInterface;
import com.xrade.utils.JsonHandler;

public class AdminNoAuthResourceGuard extends HttpRequestGuard implements ResourceGuardInterface {

	

	public void canActivate(HttpServletRequest request, HttpServletResponse response) 
			throws InvalidAdminPassphraseException, InvalidBodyFormatException {
		//Check authorisation token
		//The bearer token
		//And the content type
		//handle the validation of the request body
		
		try{
			//Check the special header for admins
			if ( request.getHeader("Admin-Passphrase").equals("admin") ){ //passphrase will be changed later
			}else{
				throw new InvalidAdminPassphraseException("");
			}
		}catch(Exception e){
			throw new InvalidAdminPassphraseException("");
		}
		
	
		
		
		//Update the resoponse
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
		response.setHeader("Access-Control-Max-Age", "1209600");
		
		
		//Try to read the body if exist
		System.out.println("Reading the body of the request\n\n" + request.getMethod());
		if ( request.getMethod().toLowerCase().equals("post") || request.getMethod().toLowerCase().equals("put") ){
			JSONObject bodyData;
			try {
				bodyData = JsonHandler.toJSON(request.getReader());
				System.out.println(bodyData.toString());
				
				request.setAttribute("body", bodyData);
				//Set the user to the body
				
				
			} catch (Exception e) {
				throw new InvalidBodyFormatException("Bad format of the request body");
			}
		}	
		
		
	}
	
	public AdminNoAuthResourceGuard(ServletContext context, HttpServletRequest request, HttpServletResponse response,
			ResourceGrantedStateListener listener) throws ServletException, IOException {
		super(context, request, response, listener);
		
		
		try {
			this.canActivate(request, response);
			
			if(listener != null){
				listener.onGranted(context, request, response);
			}
		} catch (InvalidAdminPassphraseException e) {
			//Request does not come from an admin brower
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			if(listener != null){
				listener.onDenied(e, context, request, response);
			}
		} catch (InvalidBodyFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			
			if(listener != null){
				listener.onDenied(e, context, request, response);
			}
		}
	}

}