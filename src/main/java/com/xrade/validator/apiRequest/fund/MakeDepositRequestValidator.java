package com.xrade.validator.apiRequest.fund;

import org.json.JSONObject;

import com.xrade.entity.MemberDepositRequestEntity;
import com.xrade.entity.MemberEntity;
import com.xrade.validator.AbstractRequestValidator;

public class MakeDepositRequestValidator extends AbstractRequestValidator<MemberDepositRequestEntity> {
	
	public  static final String FIELD_PAYMENT =  "payment";
	public  static final String FIELD_AMOUNT = "amount";
	public  static final String FIELD_AMOUNT_BEFORE = "amount_before";
	public  static final String FIELD_AMOUNT_AFTER = "amount_after";
	public  static final String FIELD_METADATA = "metadata";
	public  static final String FIELD_FILLED = "filled";
	public  static final String FIELD_DATE = "date";
	
	public  static final String FIELD_MEMBER_ACCOUNT_ID = "member_account_id";

	@Override
	public MemberDepositRequestEntity validate(JSONObject request) throws Exception {
		
		MemberDepositRequestEntity depositEntity = new MemberDepositRequestEntity();
		
		/*
		 * For each field test existence
		 * and after test value requirement
		 * 
		 * */
		
		try{
			long account_id = this.validateMemberAccountId(FIELD_MEMBER_ACCOUNT_ID, request);
			depositEntity.setMemberAccountId(account_id);
		}catch(Exception e){
			throw e;
		}
		
		try {
			String payment = this.validatePayment(FIELD_PAYMENT, request);
			depositEntity.setPayment(payment);
		}catch(Exception e){
			throw e;
		}
		
		try{
			int amount = this.validateAmount(FIELD_AMOUNT, request);
			depositEntity.setAmount(amount);
		}catch(Exception e){
			throw e;
		}
		
		try{
			int amountBefore = this.validateAmountBefore(FIELD_AMOUNT_BEFORE, request);
			depositEntity.setAmountBefore(amountBefore);
		}catch(Exception e){
			throw e;
		}
		
		try{
			int amountAfter = this.validateAmountAfter(FIELD_AMOUNT_AFTER, request);
			depositEntity.setAmountAfter(amountAfter);
		}catch(Exception e){
			throw e;
		}
		
		try{
			String  metadata = this.validateMetadata(FIELD_METADATA, request);
			depositEntity.setMetadata(metadata);
		}catch(Exception e){
			throw e;
		}
		
		try{
			boolean filled = this.validateFilled(FIELD_FILLED, request);
			depositEntity.setFilled(filled);
		}catch(Exception e){
			throw e;
		}
		
		try{
			String  date = this.validateDate(FIELD_DATE, request);
			depositEntity.setDate(date);
		}catch(Exception e){
			throw e;
		}
		
		
		
				
		return depositEntity;
	}

	@Override
	public MemberDepositRequestEntity validate(MemberDepositRequestEntity obj) {
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
	
	private int validateAmount(String fieldname, JSONObject request) throws Exception{
		try{
			return  Integer.parseInt(this.getFieldValue(fieldname, request));
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
