package com.xrade.forms;

import javax.servlet.http.HttpServletRequest;

import com.xrade.entity.MemberDepositRequestEntity;
import com.xrade.validator.factory.FormValidatorFactory;

public class MemberDepositForm extends DefaultForm<MemberDepositRequestEntity>{

	@Override
	public void handleRequest(HttpServletRequest request) throws Exception {
		try{
			this.data = FormValidatorFactory.getUpdateDepositRequestFormValidator().validate(request);
		}catch(Exception e){
			this.setError("message", e.getMessage());
			throw e;
		}
		
	}

}
