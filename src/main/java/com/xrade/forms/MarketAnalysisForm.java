package com.xrade.forms;

import javax.servlet.http.HttpServletRequest;

import com.xrade.entity.MarketAnalysisEntity;
import com.xrade.validator.factory.FormValidatorFactory;

public class MarketAnalysisForm extends DefaultForm<MarketAnalysisEntity> {
	
	public MarketAnalysisForm(){
		super();
	}

	@Override
	public void handleRequest(HttpServletRequest request) throws Exception {
		try{
			this.data = FormValidatorFactory.getSaveMarketAnanlysisFormValidato().validate(request);
		}catch(Exception e){
			this.setError("message", e.getMessage());
			throw e;
		}
	}
	
}
