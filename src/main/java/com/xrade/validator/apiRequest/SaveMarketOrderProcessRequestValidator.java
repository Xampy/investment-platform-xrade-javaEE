package com.xrade.validator.apiRequest;

import org.json.JSONObject;

import com.xrade.entity.MarketOrderProcessEntity;
import com.xrade.validator.AbstractRequestValidator;

public class SaveMarketOrderProcessRequestValidator extends AbstractRequestValidator<MarketOrderProcessEntity> {

	public static final String FIELD_LAST_POSITION = "last_position";
	public static final String FIELD_STATUS = "status";
	public static final String FIELD_CLOSE_DATE = "close_date";
	public static final String FIELD_BENEFIT = "benefit";

	public static final String FIELD_MARKET_ORDER_ID = "market_order_id";

	@Override
	public MarketOrderProcessEntity validate(JSONObject request) throws Exception {
		MarketOrderProcessEntity marketOrderProcessEntity = new MarketOrderProcessEntity();

		/*
		 * For each field test existence and after test value requirement
		 * 
		 */

		try {
			double lastPosition = this.validateLastPosition(FIELD_LAST_POSITION, request);
			marketOrderProcessEntity.setLastPosition(lastPosition);
		} catch (Exception e) {
			throw e; // Just only thows the exception
		}
		
		try {
			String status = this.validateStatus(FIELD_STATUS, request);
			marketOrderProcessEntity.setStatus(status);
		} catch (Exception e) {
			throw e; // Just only thows the exception
		}
		
		try {
			String closeDate = this.validateCloseDate(FIELD_CLOSE_DATE, request);
			marketOrderProcessEntity.setCloseDate(closeDate);
		} catch (Exception e) {
			throw e; // Just only thows the exception
		}
		
		try {
			int benefit = this.validateBenefit(FIELD_BENEFIT, request);
			marketOrderProcessEntity.setBenefit(benefit);
		} catch (Exception e) {
			throw e; // Just only thows the exception
		}
		
		try {
			int marketOrderId = this.validateBenefit(FIELD_MARKET_ORDER_ID, request);
			marketOrderProcessEntity.setMarketOrderId(marketOrderId);
		} catch (Exception e) {
			throw e; // Just only thows the exception
		}
		
		return marketOrderProcessEntity;
	}

	@Override
	public MarketOrderProcessEntity validate(MarketOrderProcessEntity obj) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public double validateLastPosition(String fieldname, JSONObject request) throws Exception{
		try{
			return  Double.parseDouble(this.getFieldValue(fieldname, request));
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
	
	public String validateStatus(String fieldname, JSONObject request) throws Exception{
		try{
			return  (this.getFieldValue(fieldname, request));
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
	
	public String validateCloseDate(String fieldname, JSONObject request) throws Exception{
		try{
			return  (this.getFieldValue(fieldname, request));
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
	
	public int  validateBenefit(String fieldname, JSONObject request) throws Exception{
		try{
			return  Integer.parseInt(this.getFieldValue(fieldname, request));
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
	
	public int  validateMarketOrderId(String fieldname, JSONObject request) throws Exception{
		try{
			return  Integer.parseInt(this.getFieldValue(fieldname, request));
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}


}
