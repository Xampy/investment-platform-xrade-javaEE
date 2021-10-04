package com.xrade.validator.custom;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validator on email using regex
 * 
 * Regex to restrict no. of characters in top level domain
 * 
 * @author Software
 *
 */
public class EmailRestrictionValidator {
	
	 
	public static final String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
	private static Pattern pattern = Pattern.compile(regex);
	
	public String validate(String email) throws Exception{
		
		Matcher matcher = pattern.matcher(email);
		if(matcher.matches()){
			return email;
		}
		
		throw new Exception("The provided email is invalid...Please review your email");
	}
	
}
