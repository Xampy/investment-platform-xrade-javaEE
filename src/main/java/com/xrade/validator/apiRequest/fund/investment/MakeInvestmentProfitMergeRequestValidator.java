package com.xrade.validator.apiRequest.fund.investment;

import org.json.JSONObject;

import com.xrade.entity.MemberInvestmentProfitMergeRequestEntity;
import com.xrade.validator.AbstractRequestValidator;
import com.xrade.validator.factory.CustomValidatorFactory;

public class MakeInvestmentProfitMergeRequestValidator extends AbstractRequestValidator<MemberInvestmentProfitMergeRequestEntity> {
	
	public  static final String FIELD_AMOUNT = "amount";
	
	@Override
	public MemberInvestmentProfitMergeRequestEntity validate(JSONObject request) throws Exception {
		MemberInvestmentProfitMergeRequestEntity mergeEntity = new MemberInvestmentProfitMergeRequestEntity();
		
		/*
		 * For each field test existence
		 * and after test value requirement
		 * 
		 * */
		
		try{
			double amount = this.validateAmount(FIELD_AMOUNT, request);
			mergeEntity.setAmount(amount);
		}catch(Exception e){
			throw e;
		}
		
		return mergeEntity;
	}

	@Override
	public MemberInvestmentProfitMergeRequestEntity validate(MemberInvestmentProfitMergeRequestEntity obj) {
		// TODO Auto-generated method stub
		return null;
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

}
