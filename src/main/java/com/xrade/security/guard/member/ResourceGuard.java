package com.xrade.security.guard.member;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import com.xrade.exception.resource.InvalidBodyFormatException;
import com.xrade.exception.resource.InvalidContentTypeException;
import com.xrade.exception.resource.MissedBearerTokenException;
import com.xrade.exception.resource.MissedTokenException;
import com.xrade.exception.resource.TokenVerificationErrorException;
import com.xrade.security.JwtTokenManager;
import com.xrade.security.guard.HttpRequestGuard;
import com.xrade.security.guard.ResourceGrantedStateListener;
import com.xrade.security.guard.ResourceGuardInterface;
import com.xrade.utils.JsonHandler;



/**
 * Default resource guard Controlling 
 * Headers option
 * 
 * @author Software
 *
 */
public class ResourceGuard extends HttpRequestGuard implements ResourceGuardInterface {

	

	public void canActivate(HttpServletRequest request, HttpServletResponse response) 
			throws MissedTokenException, MissedBearerTokenException, TokenVerificationErrorException,
			InvalidContentTypeException, InvalidBodyFormatException {
		//Check authorisation token
		//The bearer token
		//And the content type
		//handle the validation of the request body
		
		try{
			if ( request.getHeader("Content-Type").equals("application/json") ){
			}else{
				throw new InvalidContentTypeException("Content-Type header must be json type");
			}
		}catch(Exception e){
			throw new InvalidContentTypeException("Missed Content-Type header and it  must ne json type");
		}
		
		
		//Update the resoponse
		response.setContentType("application/json");
		/*response.setCharacterEncoding("utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
		response.setHeader("Access-Control-Max-Age", "1209600");*/
		
		
		String authorization = request.getHeader("Authorization");
		
		if(authorization == null || authorization.equals("") ){
			throw new MissedTokenException("Missed token in header");
		}else {
			//Chek  the token
			System.out.println("Token " + authorization);
			if(authorization.startsWith("Bearer") && authorization.length() > 7 ){
				String token = authorization.substring(7);
				
				System.out.println("Token " + token);
				
				//Verify the token
				JwtTokenManager tokenManager = new JwtTokenManager();
				Optional<String[]> payloadsContainer = tokenManager.verifyToken(token);
				if(payloadsContainer.isPresent()){
					//Auth is OK
					//Update request attributes
					try{
						String[] d = payloadsContainer.get();
						request.setAttribute("credentials", d);
					}catch(NoSuchElementException e){
						throw new TokenVerificationErrorException("Comprupted token");
					}
					
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
					
					
				}else {
					throw new TokenVerificationErrorException("Invalid token or Error while parsing the token");
				}
				
				
			}else{
				throw new MissedBearerTokenException("Missed token in Bearer token header");
			}
		}
		
	}
	
	public ResourceGuard(ServletContext context, HttpServletRequest request, HttpServletResponse response,
			ResourceGrantedStateListener listener) throws ServletException, IOException {
		super(context, request, response, listener);
		
		
		try {
			this.canActivate(request, response);
			
			if(listener != null){
				listener.onGranted(context, request, response);
			}
		} catch (MissedTokenException e) {
			e.printStackTrace();
			
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			
			if(listener != null){
				listener.onDenied(e, context, request, response);
			}
		} catch (MissedBearerTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			if(listener != null){
				listener.onDenied(e, context, request, response);
			}
		} catch (TokenVerificationErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			if(listener != null){
				listener.onDenied(e, context, request, response);
			}
		} catch (InvalidContentTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			
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
