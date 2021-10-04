package com.xrade.validator.apiRequest.member;

import org.json.JSONObject;

import com.xrade.entity.MemberEntity;
import com.xrade.validator.AbstractRequestValidator;
import com.xrade.validator.factory.CustomValidatorFactory;

public class RegisterMemberRequestValidator extends AbstractRequestValidator<MemberEntity>{
	
	protected static final String FIELD_LASTNAME = "lastname";
	protected static final String FIELD_FIRSTNAME = "firstname";
	protected static final String FIELD_EMAIL = "email";
	protected static final String FIELD_COUNTRY = "country";
	protected static final String FIELD_PHONE = "phone";
	protected static final String FIELD_REFERENCE = "reference";
	protected static final String FIELD_REFERENCED = "referenced";
	
	protected static final String FIELD_PASSWORD = "password";

	@Override
	public MemberEntity validate(JSONObject request) throws Exception {
		
		MemberEntity memberEntity = new MemberEntity();
		
		/*
		 * For each field test existence
		 * and after test value requirement
		 * 
		 * */
		try{
			String lastname = this.validateLastname(FIELD_LASTNAME, request);
			memberEntity.setLastname(lastname);
		}catch(Exception e){
			throw e;
		}
		
		try{
			String firstname = this.validateFirstname(FIELD_FIRSTNAME, request);
			memberEntity.setFirstname(firstname);
		}catch(Exception e){
			throw e;
		}
		
		try{
			String email = this.validateEmail(FIELD_EMAIL, request);
			memberEntity.setEmail(email);
		}catch(Exception e){
			throw e;
		}
		
		
		try{
			String country = this.validateCountry(FIELD_COUNTRY, request);
			memberEntity.setCountry(country);
		}catch(Exception e){
			throw e;
		}
		
		try{
			String phone = this.validatePhone(FIELD_PHONE, request);
			memberEntity.setPhone(phone);
		}catch(Exception e){
			throw e;
		}
		
		try{
			String reference = this.validateReference(FIELD_REFERENCE, request);
			memberEntity.setReference(reference);
		}catch(Exception e){
			throw e;
		}
		
		try{
			String referenced = this.validateReferenced(FIELD_REFERENCED, request);
			memberEntity.setReferenced(referenced);
		}catch(Exception e){
			throw e;
		}
		
		try{
			String password = this.validatePassword(FIELD_PASSWORD, request);
			memberEntity.setPassword(password);
		}catch(Exception e){
			throw e;
		}
	
		
		return memberEntity;
	}

	@Override
	public MemberEntity validate(MemberEntity obj) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	protected String validateLastname(String fieldname, JSONObject request) throws Exception{
		try{
			return  this.getFieldValue(fieldname, request);
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
	
	protected String validateFirstname(String fieldname, JSONObject request) throws Exception{
		try{
			return  this.getFieldValue(fieldname, request);
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
	
	protected String validateEmail(String fieldname, JSONObject request) throws Exception{
		try{
			String email =  this.getFieldValue(fieldname, request);
			
			try{
				return CustomValidatorFactory.getEmailRestrictionValidator().validate(email);
			}catch(Exception e){
				throw e;
			}
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
	
	protected String validateCountry(String fieldname, JSONObject request) throws Exception{
		try{
			return  this.getFieldValue(fieldname, request);
			
			//Check that the country figure in the list
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
	
	protected String validatePhone(String fieldname, JSONObject request) throws Exception{
		try{
			return  this.getFieldValue(fieldname, request);
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
	
	protected String validateReference(String fieldname, JSONObject request) throws Exception{
		try{
			return  this.getFieldValue(fieldname, request);
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
	
	protected String validateReferenced(String fieldname, JSONObject request) throws Exception{
		try{
			return  this.getFieldValue(fieldname, request);
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
	
	protected String validatePassword(String fieldname, JSONObject request) throws Exception{
		try{
			return  this.getFieldValue(fieldname, request);
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
	
}
