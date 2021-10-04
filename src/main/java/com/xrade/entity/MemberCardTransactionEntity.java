package com.xrade.entity;

import org.json.JSONObject;

import com.xrade.models.user.transaction.MemberCardTransaction;

public class MemberCardTransactionEntity extends MemberCardTransaction implements EntityInterface {
	
	
	

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
		// TODO Auto-generated method stub
		return null;
	}

}
