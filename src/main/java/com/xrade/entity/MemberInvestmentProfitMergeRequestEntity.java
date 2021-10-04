package com.xrade.entity;

import org.json.JSONObject;

import com.xrade.models.user.MemberInvestmentProfitMergeRequest;

public class MemberInvestmentProfitMergeRequestEntity extends MemberInvestmentProfitMergeRequest implements EntityInterface {
	
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
		
		json.put("id", this.getId());
		json.put("amount", this.getAmount());
		json.put("status", this.status);
		
		
		return json;
	}

}
