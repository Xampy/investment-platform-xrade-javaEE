package com.xrade.validator.apiRequest.fund.investment;

import org.json.JSONObject;

import com.xrade.entity.MemberInvestmentProfitWithdrawalRequestEntity;
import com.xrade.models.user.MemberInvestmentProfitWithdrawalRequest;
import com.xrade.validator.AbstractRequestValidator;
import com.xrade.validator.factory.CustomValidatorFactory;

public class MakeInvestmentProfitWithdrawRequestValidator  extends AbstractRequestValidator<MemberInvestmentProfitWithdrawalRequestEntity> {
	
	
	public  static final String FIELD_PAYMENT =  "payment";
	public  static final String FIELD_AMOUNT = "amount";
	public  static final String FIELD_AMOUNT_BEFORE = "amount_before";
	public  static final String FIELD_AMOUNT_AFTER = "amount_after";
	public  static final String FIELD_METADATA = "metadata";
	public  static final String FIELD_FILLED = "filled";
	public  static final String FIELD_DATE = "date";
	
	public  static final String FIELD_INTEREST_ACCOUNT_ID = "interest_account_id";
	
	@Override
	public MemberInvestmentProfitWithdrawalRequestEntity validate(JSONObject request) throws Exception {
		MemberInvestmentProfitWithdrawalRequestEntity withdrawEntity = new MemberInvestmentProfitWithdrawalRequestEntity();
		
		/*
		 * For each field test existence
		 * and after test value requirement
		 * 
		 * */
		
		/*try{
			long account_id = this.validateMemberAccountId(FIELD_MEMBER_ACCOUNT_ID, request);
			withdrawEntity.setMemberAccountId(account_id);
		}catch(Exception e){
			throw e;
		}*/
		
		try {
			String payment = this.validatePayment(FIELD_PAYMENT, request);
			withdrawEntity.setPayment(payment);
		}catch(Exception e){
			throw e;
		}
		
		try{
			double amount = this.validateAmount(FIELD_AMOUNT, request);
			withdrawEntity.setAmount(amount);
		}catch(Exception e){
			throw e;
		}
		
		/*try{
			int amountBefore = this.validateAmountBefore(FIELD_AMOUNT_BEFORE, request);
			withdrawEntity.setAmountBefore(amountBefore);
		}catch(Exception e){
			throw e;
		}*/
		
		/*try{
			int amountAfter = this.validateAmountAfter(FIELD_AMOUNT_AFTER, request);
			withdrawEntity.setAmountAfter(amountAfter);
		}catch(Exception e){
			throw e;
		}*/
		
		try{
			String  metadata = this.validateMetadata(FIELD_METADATA, request);
			withdrawEntity.setMetadata(metadata);
		}catch(Exception e){
			throw e;
		}
		
		/*try{
			boolean filled = this.validateFilled(FIELD_FILLED, request);
			withdrawEntity.setFilled(filled);
		}catch(Exception e){
			throw e;
		}
		
		try{
			String  date = this.validateDate(FIELD_DATE, request);
			withdrawEntity.setDate(date);
		}catch(Exception e){
			throw e;
		}*/
		
		
		
				
		return withdrawEntity;
	}

	@Override
	public MemberInvestmentProfitWithdrawalRequestEntity validate(MemberInvestmentProfitWithdrawalRequestEntity obj) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	private long validateMemberAccountId(String fieldname, JSONObject request) throws Exception{
		try{
			return  Long.parseLong(this.getFieldValue(fieldname, request));
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
	
	private String validatePayment(String fieldname, JSONObject request) throws Exception{
		try{
			return this.getFieldValue(fieldname, request);
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
	
	private double validateAmount(String fieldname, JSONObject request) throws Exception{
		try{
			double amount = Double.parseDouble(this.getFieldValue(fieldname, request));
			try{
				return CustomValidatorFactory.getAmountRestrictionValidator().validate(amount);
			}catch(Exception e){
				throw e;
			}
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
	
	private int validateAmountBefore(String fieldname, JSONObject request) throws Exception{
		try{
			return  Integer.parseInt(this.getFieldValue(fieldname, request));
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
	
	private int validateAmountAfter(String fieldname, JSONObject request) throws Exception{
		try{
			return  Integer.parseInt(this.getFieldValue(fieldname, request));
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
	
	private String validateMetadata(String fieldname, JSONObject request) throws Exception{
		try{
			return this.getFieldValue(fieldname, request);
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
	
	private boolean validateFilled(String fieldname, JSONObject request) throws Exception{
		try{
			return Boolean.parseBoolean(this.getFieldValue(fieldname, request));
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}
	
	private String validateDate(String fieldname, JSONObject request) throws Exception{
		try{
			return this.getFieldValue(fieldname, request);
		}catch (Exception e) {
			throw new Exception("The field " + fieldname + " is required");
		}
	}

}
