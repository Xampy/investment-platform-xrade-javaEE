package com.xrade.validator.apiRequest;

import org.json.JSONObject;

import com.xrade.entity.MarketOrderEntity;
import com.xrade.entity.MarketOrderProcessEntity;

public class UpdateMarketOrderProcessValidator extends SaveMarketOrderProcessRequestValidator {
	
	public static final String FIELD_MARKET_ORDER_PROCESS_ID = "market_order_process_id";
	
	
	@Override
	public MarketOrderProcessEntity validate(JSONObject request) throws Exception {
		MarketOrderProcessEntity process = super.validate(request);
		try{
			long processId = this.validateMarketOrderProcessId(FIELD_MARKET_ORDER_PROCESS_ID, request);
			process.setId(processId);
		}catch( Exception e ){
			throw e; //Just only thows the exception
		}
		
		
		return process;
	}
	
	public long  validateMarketOrderProcessId(String fieldname, JSONObject request) throws Exception{
		try{
			return Long.parseLong(this.getFieldValue(fieldname, request));
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
}
