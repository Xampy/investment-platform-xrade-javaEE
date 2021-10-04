package com.xrade.validator.form;

import javax.servlet.http.HttpServletRequest;



import com.xrade.entity.MarketAnalysisEntity;
import com.xrade.validator.AbstractFormValidator;

public class SaveMarketAnanlysisFormValidator extends AbstractFormValidator<MarketAnalysisEntity> {
	
	public static final String FIELD_MARKET = "market";
	public static final String FIELD_TOTAL_LOT = "total_lot";
	public static final String FIELD_PRICE = "price";
	public static final String FIELD_MAX_PROFIT = "max_profit";
	public static final String FIELD_MAX_LOSS = "max_loss";
	
	public static final String FIELD_ANALYSIS_DATA_ID = "analysis_data_id";

	@Override
	public MarketAnalysisEntity validate(HttpServletRequest request) throws Exception {
		MarketAnalysisEntity marketAnalysisEntity = new MarketAnalysisEntity();

		/*
		 * For each field test existence
		 * and after test value requirement
		 * 
		 * */
		
		try{
			String market = this.validateMarket(FIELD_MARKET, request);
			marketAnalysisEntity.setMarket(market);
		}catch( Exception e ){
			throw e; //Just only thows the exception
		}
		
		try{
			int  totalLot = this.validateTotalLot(FIELD_TOTAL_LOT, request);
			marketAnalysisEntity.setTotalLot(totalLot);
		}catch( Exception e ){
			throw e; //Just only thows the exception
		}
		
		try{
			int  price = this.validatePrice(FIELD_PRICE, request);
			marketAnalysisEntity.setPrice(price);
		}catch( Exception e ){
			throw e; //Just only thows the exception
		}
		
		try{
			int  maxProfit = this.validateMaxProfit(FIELD_MAX_PROFIT, request);
			marketAnalysisEntity.setMaxProfit(maxProfit);
		}catch( Exception e ){
			throw e; //Just only thows the exception
		}
		
		try{
			int  maxLoss = this.validateMaxLoss(FIELD_MAX_LOSS, request);
			marketAnalysisEntity.setMaxLoss(maxLoss);
		}catch( Exception e ){
			throw e; //Just only thows the exception
		}
		
		try{
			int  maxLoss = this.validateMaxLoss(FIELD_MAX_LOSS, request);
			marketAnalysisEntity.setMaxLoss(maxLoss);
		}catch( Exception e ){
			throw e; //Just only thows the exception
		}
		
		try{
			long  analysisDataId = this.validateAnalysisDataId(FIELD_ANALYSIS_DATA_ID, request);
			marketAnalysisEntity.setAnalysisDataId(analysisDataId);
		}catch( Exception e ){
			throw e; //Just only thows the exception
		}
		
		
		
		return marketAnalysisEntity;
	}

	@Override
	public MarketAnalysisEntity validate(MarketAnalysisEntity obj) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public String validateMarket(String fieldname, HttpServletRequest request) throws Exception{
		try{
			return  this.getFieldValue(fieldname, request);
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
	
	public int validateTotalLot(String fieldname, HttpServletRequest request) throws Exception{
		try{
			return  Integer.parseInt(this.getFieldValue(fieldname, request));
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
	
	public int validatePrice(String fieldname, HttpServletRequest request) throws Exception{
		try{
			return  Integer.parseInt(this.getFieldValue(fieldname, request));
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
	
	public int validateMaxProfit(String fieldname, HttpServletRequest request) throws Exception{
		try{
			return  Integer.parseInt(this.getFieldValue(fieldname, request));
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
	
	public int validateMaxLoss(String fieldname, HttpServletRequest request) throws Exception{
		try{
			return  Integer.parseInt(this.getFieldValue(fieldname, request));
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
	
	public long validateAnalysisDataId(String fieldname, HttpServletRequest request) throws Exception{
		try{
			return  Long.parseLong(this.getFieldValue(fieldname, request));
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}

}
