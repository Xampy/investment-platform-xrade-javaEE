package com.xrade.security;

import java.util.Optional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

/**
 * The JwtTokenManager class hold otken actions like generating and verification
 * @author Software
 *
 */
public class JwtTokenManager {
	
	public static final String SECRET = "Moana";
	
	public String generateToken(String[] payload) throws JWTCreationException  {
		try {
		    Algorithm algorithm = Algorithm.HMAC256(SECRET);
		    String token = JWT.create()
		        .withIssuer("auth0")
		        .withArrayClaim("credentials", payload)
		        .sign(algorithm);
		    
		    return token;
		} catch (JWTCreationException exception){
		    //Do something
			throw new JWTCreationException(null, exception);
		} 
	}
	
	
	public String generateToken() throws JWTCreationException  {
		try {
		    Algorithm algorithm = Algorithm.HMAC256(SECRET);
		    String token = JWT.create()
		        .withIssuer("auth0")
		        .sign(algorithm);
		    
		    return token;
		} catch (JWTCreationException exception){
		    //Do something
			throw new JWTCreationException(null, exception);
		} 
	}
	
	public Optional<String[]> verifyToken(String token){
		try {
		    Algorithm algorithm = Algorithm.HMAC256(SECRET);
		    JWTVerifier verifier = JWT.require(algorithm)
		        .withIssuer("auth0")
		        .build(); //Reusable verifier instance
		    DecodedJWT jwt = verifier.verify(token);
		    String payloads[] = jwt.getClaim("credentials").asArray(String.class);
		    for(String p: payloads)
		    	System.out.println("Payloads " + p);
		    
		    if(payloads.length == 3){
		    	return Optional.of(payloads);
		    }
		} catch (JWTVerificationException exception){
		    
		}
		
		return Optional.empty();
	}
}
