package com.xrade.forms;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.xrade.entity.AnalysisDataEntity;
import com.xrade.models.analysis.AnalysisData;
import com.xrade.validator.factory.FormValidatorFactory;


public final class AnalysisDataForm extends DefaultForm<AnalysisDataEntity>{

	@Override
	public void handleRequest(HttpServletRequest request) throws Exception {
		try{
			this.data = FormValidatorFactory.getSaveAnalysisDataFormValidator().validate(request);
		}catch(Exception e){
			this.setError("message", e.getMessage());
			throw e;
		}
	}
	
	
}
