package com.xrade.forms;

import javax.servlet.http.HttpServletRequest;

import com.xrade.entity.BackOfficerEntity;
import com.xrade.validator.factory.FormValidatorFactory;

public class BackOfficerLoginForm extends DefaultForm<BackOfficerEntity>{
	
	public BackOfficerLoginForm(){
		super();
	}
	
	@Override
	public void handleRequest(HttpServletRequest request) throws Exception {
		try{
			this.data = FormValidatorFactory.getBackOfficerLoginFormValidator().validate(request);
		}catch(Exception e){
			this.setError("message", e.getMessage());
			throw e;
		}
		
	}

}
