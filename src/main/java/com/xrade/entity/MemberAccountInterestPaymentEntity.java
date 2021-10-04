package com.xrade.entity;

import org.json.JSONObject;

import com.xrade.models.user.MemberAccountInterestPayment;

public class MemberAccountInterestPaymentEntity extends MemberAccountInterestPayment implements EntityInterface {
	
	/**
	 * Primary key
	 * 
	 */
	private long id;
	
	/**
	 * ManyToOne
	 */
	private long memberAccountId;
	
	
	public MemberAccountInterestPaymentEntity() {
		super();
		// TODO Auto-generated constructor stub
	}


	



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
		
		json.put("id", this.getId());
		json.put("amount_before", this.getAmountBefore());
		json.put("amount_after", this.getAmountAfter());
		json.put("due_date", this.getCreatedAt());
		
		return json;
	}

}
