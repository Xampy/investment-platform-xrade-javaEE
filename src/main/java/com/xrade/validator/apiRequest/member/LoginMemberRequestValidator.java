package com.xrade.validator.apiRequest.member;

import org.json.JSONObject;

import com.xrade.entity.MemberEntity;

public class LoginMemberRequestValidator extends RegisterMemberRequestValidator {

	@Override
	public MemberEntity validate(JSONObject request) throws Exception {
		
		MemberEntity memberEntity = new MemberEntity();
		
		/*
		 * For each field test existence
		 * and after test value requirement
		 * 
		 * */
		
		try{
			String email = this.validateEmail(FIELD_EMAIL, request);
			memberEntity.setEmail(email);
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
}
