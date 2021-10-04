package com.xrade.validator.form;

import javax.servlet.http.HttpServletRequest;

import com.xrade.entity.BackOfficerEntity;
import com.xrade.entity.MarketAnalysisEntity;
import com.xrade.validator.AbstractFormValidator;

public class BackOfficerLoginFormValidator extends AbstractFormValidator<BackOfficerEntity> {
	
	public static final String FIELD_EMAIL = "email";
	public static final String FIELD_PASSWORD = "password";

	@Override
	public BackOfficerEntity validate(HttpServletRequest request) throws Exception {
		BackOfficerEntity userEntity = new BackOfficerEntity();

		/*
		 * For each field test existence
		 * and after test value requirement
		 * 
		 * */
		
		try{
			String email = this.validateEmail(FIELD_EMAIL, request);
			userEntity.setEmail(email);
		}catch( Exception e ){
			throw e; //Just only thows the exception
		}
		
		try{
			String password = this.validatePassword(FIELD_PASSWORD, request);
			userEntity.setPassword(password);
		}catch( Exception e ){
			throw e; //Just only thows the exception
		}
		
		return userEntity;
	}

	@Override
	public BackOfficerEntity validate(BackOfficerEntity obj) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public String validateEmail(String fieldname, HttpServletRequest request) throws Exception{
		try{
			return  this.getFieldValue(fieldname, request);
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
	
	public String validatePassword(String fieldname, HttpServletRequest request) throws Exception{
		try{
			return  this.getFieldValue(fieldname, request);
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}

}
