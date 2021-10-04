package com.xrade.validator.apiRequest;

import org.json.JSONObject;
import com.xrade.entity.MarketOrderEntity;
import com.xrade.models.order.OrderType;
import com.xrade.validator.AbstractRequestValidator;

public class SaveMarketOrderRequestValidator extends AbstractRequestValidator<MarketOrderEntity> {
	
	public static final String FIELD_POSITION = "position";
	public static final String FIELD_STOP_LOSS = "stop_loss";
	public static final String FIELD_TAKE_PROFIT = "take_profit";
	public static final String FIELD_LOT = "lot";
	public static final String FIELD_ORDER_TYPE = "order_type";
	public static final String FIELD_AMOUNT = "amount";
	
	public static final String FIELD_MARKET_ANALYSIS_ID = "market_analaysis_id";
	public static final String FIELD_MEMBER_ID = "member_id";
	
	@Override
	public MarketOrderEntity validate(JSONObject request) throws Exception {
		
		MarketOrderEntity marketOrderEntity = new MarketOrderEntity();
		
		/*
		 * For each field test existence
		 * and after test value requirement
		 * 
		 * */
		
		try{
			double position = this.validatePosition(FIELD_POSITION, request);
			marketOrderEntity.setPosition(position);
		}catch( Exception e ){
			throw e; //Just only thows the exception
		}
		
		try{
			int stopLoss  = this.validateStopLoss(FIELD_STOP_LOSS, request);
			marketOrderEntity.setStopLoss(stopLoss);
		}catch( Exception e ){
			throw e; //Just only thows the exception
		}
		
		try{
			int takeProfit  = this.validateTakeProfit(FIELD_TAKE_PROFIT, request);
			marketOrderEntity.setTakeProfit(takeProfit);
		}catch( Exception e ){
			throw e; //Just only thows the exception
		}
		
		try{
			int lot  = this.validateLot(FIELD_LOT, request);
			marketOrderEntity.setLot(lot);
		}catch( Exception e ){
			throw e; //Just only thows the exception
		}
		
		try{
			int amount  = this.validateAmount(FIELD_AMOUNT, request);
			marketOrderEntity.setAmount(amount);
		}catch( Exception e ){
			throw e; //Just only thows the exception
		}
		
		try{
			OrderType type  = this.validateType(FIELD_ORDER_TYPE, request);
			marketOrderEntity.setType(type);
		}catch( Exception e ){
			throw e; //Just only thows the exception
		}
		
		try{
			long marketAnalysisId  = this.validateMarketAnalysisId(FIELD_MARKET_ANALYSIS_ID, request);
			marketOrderEntity.setMarketAnalysisId(marketAnalysisId);
		}catch( Exception e ){
			throw e; //Just only thows the exception
		}
		
		try{
			long memberId  = this.validateMemberId(FIELD_MEMBER_ID, request);
			marketOrderEntity.setMemberId(memberId);
		}catch( Exception e ){
			throw e; //Just only thows the exception
		}
		
		
		return marketOrderEntity;
	}
	@Override
	public MarketOrderEntity validate(MarketOrderEntity obj) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	public double validatePosition(String fieldname, JSONObject request) throws Exception{
		try{
			return  Double.parseDouble(this.getFieldValue(fieldname, request));
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required" + e.getMessage());
		}
	}
	
	public int validateTakeProfit(String fieldname, JSONObject request) throws Exception{
		try{
			return  Integer.parseInt(this.getFieldValue(fieldname, request));
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
	
	public int validateStopLoss(String fieldname, JSONObject request) throws Exception{
		try{
			return  Integer.parseInt(this.getFieldValue(fieldname, request));
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
	
	public int validateLot(String fieldname, JSONObject request) throws Exception{
		try{
			return  Integer.parseInt(this.getFieldValue(fieldname, request));
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
	
	public int validateAmount(String fieldname, JSONObject request) throws Exception{
		try{
			return  Integer.parseInt(this.getFieldValue(fieldname, request));
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
	
	public OrderType validateType(String fieldname, JSONObject request) throws Exception{
		try{
			return  OrderType.valueOf( this.getFieldValue(fieldname, request) );
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
	
	public long validateMarketAnalysisId(String fieldname, JSONObject request) throws Exception{
		try{
			return  Long.parseLong(this.getFieldValue(fieldname, request));
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
	
	public long validateMemberId(String fieldname, JSONObject request) throws Exception{
		try{
			return  Long.parseLong(this.getFieldValue(fieldname, request));
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
	

}
