package com.xrade.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class XStringHasher {
	
	
	public static String hashMD5(String originalString){
		
		MessageDigest md;
		try {
			
			md = MessageDigest.getInstance("MD5");
			md.update(originalString.getBytes());
		    byte[] digest = md.digest();
		    String myHash = DatatypeConverter
		      .printHexBinary(digest).toUpperCase();
		    
		    return myHash;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			e.printStackTrace();
			return originalString;
		}
	    
	}
	
	
	public static String hash256(String originalString){
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			final byte[] hashbytes = digest.digest(
					  originalString.getBytes(StandardCharsets.UTF_8));
			String sha3Hex = bytesToHex(hashbytes);
			return sha3Hex;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return originalString;
		}
		
	}
	
	private static String bytesToHex(byte[] hash) {
	    StringBuilder hexString = new StringBuilder(2 * hash.length);
	    for (int i = 0; i < hash.length; i++) {
	        String hex = Integer.toHexString(0xff & hash[i]);
	        if(hex.length() == 1) {
	            hexString.append('0');
	        }
	        hexString.append(hex);
	    }
	    return hexString.toString();
	}
}
