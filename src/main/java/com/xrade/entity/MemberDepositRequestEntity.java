package com.xrade.entity;

import org.json.JSONObject;

import com.xrade.models.user.MemberDepositRequest;

public class MemberDepositRequestEntity extends MemberDepositRequest implements EntityInterface {
	
	/**
	 * Primary key
	 * 
	 */
	private long id;
	
	/**
	 * ManyToOne
	 */
	private long memberAccountId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	

	public long getMemberAccountId() {
		return memberAccountId;
	}

	public void setMemberAccountId(long memberAccountId) {
		this.memberAccountId = memberAccountId;
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
