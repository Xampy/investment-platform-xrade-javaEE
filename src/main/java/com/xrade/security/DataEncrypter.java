package com.xrade.security;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class DataEncrypter {
	
	public static final String ENCODER_PASSWORD__SALT = "$2a$10$dnRurAP/Zx1bEi.I0Rlk8u";
	
	
	public static String encryptPassword(String password){
		return BCrypt.hashpw(password, ENCODER_PASSWORD__SALT);
	}
	
	public static String encryptToken(String token){
		return BCrypt.hashpw(token, ENCODER_PASSWORD__SALT);
	}

}
