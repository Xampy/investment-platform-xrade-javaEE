package com.xrade.entity;

import org.json.JSONObject;

import com.xrade.models.user.MemberInvestmentProfitWithdrawalRequest;

public class MemberInvestmentProfitWithdrawalRequestEntity extends MemberInvestmentProfitWithdrawalRequest implements EntityInterface {
	
	/**
	 * Primary key
	 * 
	 */
	private long id;
	
	/**
	 * ManyToOne
	 */
	private long memberInterestAccountId;
	
	
	
	
	public long getId() {
		return id;
	}




	public void setId(long id) {
		this.id = id;
	}




	public long getMemberInterestAccountId() {
		return memberInterestAccountId;
	}




	public void setMemberInterestAccountId(long memberInterestAccountId) {
		this.memberInterestAccountId = memberInterestAccountId;
	}




	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		
		json.put("payment", this.getPayment());
		json.put("id", this.getId());
		json.put("amount", this.getAmount());
		json.put("filled", this.isFilled());
		
		
		return json;
	}

}
