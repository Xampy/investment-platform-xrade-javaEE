package com.xrade.web;

import com.xrade.security.DataEncrypter;

public class AppJwtTokenGenerator {
	
	public static void main(String[] args){
		String password = "pass2000";
		System.out.println("Out app Hash password : " + hashPassword(password));
	}
	
	
	private static String hashPassword(String password){
		String hash = DataEncrypter.encryptPassword(password);
		return hash;
	}
}
