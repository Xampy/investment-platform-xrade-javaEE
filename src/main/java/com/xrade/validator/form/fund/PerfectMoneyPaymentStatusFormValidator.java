package com.xrade.validator.form.fund;

import javax.servlet.http.HttpServletRequest;

import com.xrade.entity.BackOfficerEntity;
import com.xrade.forms.PerfectMoneyPaymentStatusForm;
import com.xrade.validator.AbstractFormValidator;

public class PerfectMoneyPaymentStatusFormValidator extends AbstractFormValidator<PerfectMoneyPaymentStatusForm> {
	
	public static final String FIELD_PAYEE_ACCOUNT = "PAYEE_ACCOUNT";
	public static final String FIELD_PAYMENT_ID = "PAYMENT_ID";
	public static final String FIELD_PAYMENT_AMOUNT = "PAYMENT_AMOUNT";
	public static final String FIELD_PAYMENT_UNITS = "PAYMENT_UNITS";
	public static final String FIELD_PAYMENT_BATCH_NUM = "PAYMENT_BATCH_NUM";
	public static final String FIELD_PAYER_ACCOUNT = "PAYER_ACCOUNT";
	public static final String FIELD_TIMESTAMPGMT = "TIMESTAMPGMT";
	public static final String FIELD_V2_HASH = "V2_HASH";
	
	public static final String FIELD_SESSION_ID = "SESSION_ID";
	

	@Override
	public PerfectMoneyPaymentStatusForm validate(HttpServletRequest request) throws Exception {
		PerfectMoneyPaymentStatusForm form = new PerfectMoneyPaymentStatusForm();

		/*
		 * For each field test existence
		 * and after test value requirement
		 * 
		 * */
		
		try{
			String payeeAccount = this.validatePayeeAccount(FIELD_PAYEE_ACCOUNT, request);
			form.setPayeeAccount(payeeAccount);
		}catch( Exception e ){
			throw e; //Just only thows the exception
		}
		
		try{
			String paymentId = this.validatePaymentId(FIELD_PAYMENT_ID, request);
			form.setPaymentId(paymentId);
		}catch( Exception e ){
			throw e; //Just only thows the exception
		}
		
		try{
			double paymentAmount = this.validatePaymentAmount(FIELD_PAYMENT_AMOUNT, request);
			form.setPaymentAmount(paymentAmount);
		}catch( Exception e ){
			throw e; //Just only thows the exception
		}
		
		try{
			String paymentUnits = this.validatePaymentUnits(FIELD_PAYMENT_UNITS, request);
			form.setPaymentUnits(paymentUnits);
		}catch( Exception e ){
			throw e; //Just only thows the exception
		}
		
		try{
			String paymentBatchNum = this.validatePaymentBatchNum(FIELD_PAYMENT_BATCH_NUM, request);
			form.setPaymentBatchNum(paymentBatchNum);
		}catch( Exception e ){
			throw e; //Just only thows the exception
		}
		
		try{
			String payerAccount = this.validatePayerAccount(FIELD_PAYER_ACCOUNT, request);
			form.setPayerAccount(payerAccount);
		}catch( Exception e ){
			throw e; //Just only thows the exception
		}
		
		try{
			String timestampgmt = this.validateTimestampgmt(FIELD_TIMESTAMPGMT, request);
			form.setTimestampgmt(timestampgmt);
		}catch( Exception e ){
			throw e; //Just only thows the exception
		}
		
		try{
			String v2Hash = this.validateV2Hash(FIELD_V2_HASH, request);
			form.setV2Hash(v2Hash);
		}catch( Exception e ){
			throw e; //Just only thows the exception
		}
		
		try{
			String sessionId = this.validateSessionId(FIELD_SESSION_ID, request);
			form.setSessionId(sessionId);
		}catch( Exception e ){
			throw e; //Just only thows the exception
		}
		
		return form;
	}

	@Override
	public PerfectMoneyPaymentStatusForm validate(PerfectMoneyPaymentStatusForm obj) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public String validatePayeeAccount(String fieldname, HttpServletRequest request) throws Exception{
		try{
			return  this.getFieldValue(fieldname, request);
		}catch (Exception e) {
			throw new Exception("");
		}
	}
	
	public String validatePaymentId(String fieldname, HttpServletRequest request) throws Exception{
		try{
			return  this.getFieldValue(fieldname, request);
		}catch (Exception e) {
			throw new Exception("");
		}
	}
	
	public double validatePaymentAmount(String fieldname, HttpServletRequest request) throws Exception{
		try{
			return  Double.parseDouble(this.getFieldValue(fieldname, request));
		}catch (Exception e) {
			throw new Exception("");
		}
	}
	
	
	public String validatePaymentUnits(String fieldname, HttpServletRequest request) throws Exception{
		try{
			return  this.getFieldValue(fieldname, request);
		}catch (Exception e) {
			throw new Exception("");
		}
	}
	
	
	public String validatePaymentBatchNum(String fieldname, HttpServletRequest request) throws Exception{
		try{
			return  this.getFieldValue(fieldname, request);
		}catch (Exception e) {
			throw new Exception("");
		}
	}
	
	public String validatePayerAccount(String fieldname, HttpServletRequest request) throws Exception{
		try{
			return  this.getFieldValue(fieldname, request);
		}catch (Exception e) {
			throw new Exception("");
		}
	}
	
	public String validateTimestampgmt(String fieldname, HttpServletRequest request) throws Exception{
		try{
			return  this.getFieldValue(fieldname, request);
		}catch (Exception e) {
			throw new Exception("");
		}
	}
	
	public String validateV2Hash(String fieldname, HttpServletRequest request) throws Exception{
		try{
			return  this.getFieldValue(fieldname, request);
		}catch (Exception e) {
			throw new Exception("");
		}
	}
	
	public String validateSessionId(String fieldname, HttpServletRequest request) throws Exception{
		try{
			return  this.getFieldValue(fieldname, request);
		}catch (Exception e) {
			throw new Exception("");
		}
	}
	
	
	
	
	
	
	
	
}
