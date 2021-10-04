package com.xrade.validator.apiRequest;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.xrade.entity.AnalysisDataEntity;
import com.xrade.models.market.MarketTimeframe;
import com.xrade.models.order.OrderType;
import com.xrade.validator.AbstractRequestValidator;

public class SaveAnalysisDataRequestValidator extends AbstractRequestValidator<AnalysisDataEntity> {
	
	private static final String FIELD_POSITION = "position";
	private static final String FIELD_STOP_LOSS = "stop_loss";
	private static final String FIELD_TAKE_PROFIT = "take_profit";
	private static final String FIELD_START_TIME = "start_time";
	private static final String FIELD_END_TIME = "end_time";	
	private static final String FIELD_MARKET = "market";
	private static final String FIELD_TIMEFRAME = "timeframe";
	private static final String FIELD_ORDER_TYPE = "order_type";
	private static final String FIELD_CAPITAL = "capital";
	private static final String FIELD_PROFIT = "profit";
	private static final String FIELD_LOSS = "loss";

	@Override
	public AnalysisDataEntity validate(JSONObject request) throws Exception {
		
		AnalysisDataEntity analysisDataEntity = new AnalysisDataEntity();
		
		/*
		 * For each field test existence
		 * and after test value requirement
		 * 
		 * */
		
		try{
			double position = this.validatePosition(FIELD_POSITION, request);
			analysisDataEntity.setPosition(position);
		}catch( Exception e ){
			throw e; //Just only thows the exception
		}
		try{
			double stopLoss = this.validateStopLoss(FIELD_STOP_LOSS, request);
			analysisDataEntity.setStopLoss(stopLoss);
		}catch( Exception e ){
			throw e;
		}
		try{
			double takeProfit = this.validateTakeProfit(FIELD_TAKE_PROFIT, request);
			analysisDataEntity.setTakeProfit(takeProfit);;
		}catch( Exception e ){
			throw e;
		}
		try{
			String startTime = this.validateStartTime(FIELD_START_TIME, request);
			analysisDataEntity.setStartTime(startTime);
		}catch( Exception e ){
			throw e;
		}
		try{
			String endTime = this.validateEndTime(FIELD_END_TIME, request);
			analysisDataEntity.setEndTime(endTime);
		}catch( Exception e ){
			throw e;
		}		
		try{
			String market = this.validateMarket(FIELD_MARKET, request);
			analysisDataEntity.setMarket(market);
		}catch( Exception e ){
			throw e;
		}
		try{
			MarketTimeframe timeframe = this.validateTimeframe(FIELD_TIMEFRAME, request);
			analysisDataEntity.setTimeframe(timeframe);
		}catch( Exception e ){
			throw e;
		}
		try{
			OrderType order = this.validateOrderType(FIELD_ORDER_TYPE, request);
			analysisDataEntity.setOrder(order);
		}catch( Exception e ){
			throw e;
		}
		try{
			double capital = this.validateCapital(FIELD_CAPITAL, request);
			analysisDataEntity.setCapital(capital);
		}catch( Exception e ){
			throw e;
		}
		try{
			double profit = this.validateProfit(FIELD_PROFIT, request);
			analysisDataEntity.setProfit(profit);
		}catch( Exception e ){
			throw e;
		}
		try{
			double loss = this.validateLoss(FIELD_LOSS, request);
			analysisDataEntity.setLoss(loss);
		}catch( Exception e ){
			throw e;
		}
		
		
		
		return analysisDataEntity;
	}
	
	@Override
	public AnalysisDataEntity validate(AnalysisDataEntity obj) {
		return null;
	}
	
	
	
	private double validatePosition(String fieldname, JSONObject request) throws Exception{
		try{
			return  Double.parseDouble(this.getFieldValue(fieldname, request));
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
	
	private double validateStopLoss(String fieldname, JSONObject request) throws Exception{
		try{
			return  Double.parseDouble(this.getFieldValue(fieldname, request));
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
	
	private double validateTakeProfit(String fieldname, JSONObject request) throws Exception{
		try{
			return  Double.parseDouble(this.getFieldValue(fieldname, request));
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
	
	private double validateCapital(String fieldname, JSONObject request) throws Exception{
		try{
			return  Double.parseDouble(this.getFieldValue(fieldname, request));
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
	
	private double validateProfit(String fieldname, JSONObject request) throws Exception{
		try{
			return  Double.parseDouble(this.getFieldValue(fieldname, request));
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
	
	private double validateLoss(String fieldname, JSONObject request) throws Exception{
		try{
			return  Double.parseDouble(this.getFieldValue(fieldname, request));
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
	
	private String validateStartTime(String fieldname, JSONObject request) throws Exception{
		try{
			return  this.getFieldValue(fieldname, request);
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
	
	private String validateEndTime(String fieldname, JSONObject request) throws Exception{
		try{
			return  this.getFieldValue(fieldname, request);
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
	
	private String validateMarket(String fieldname, JSONObject request) throws Exception{
		try{
			return  this.getFieldValue(fieldname, request);
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
	
	private MarketTimeframe validateTimeframe(String fieldname, JSONObject request) throws Exception{
		try{
			return MarketTimeframe.valueOf(this.getFieldValue(fieldname, request)); //TODO May throw error here
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
	
	private OrderType validateOrderType(String fieldname, JSONObject request) throws Exception{
		try{
			return  OrderType.valueOf( this.getFieldValue(fieldname, request) );
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}

	
	
	
	
}
