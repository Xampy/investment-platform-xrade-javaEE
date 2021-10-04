package com.xrade.entity;

import org.json.JSONObject;

import com.xrade.models.user.MemberAccount;

public class MemberAccountEntity extends MemberAccount implements EntityInterface {
	
	/**
	 * Primary key
	 * 
	 */
	private long id;
	
	/**
	 * 
	 */
	private long memberId;
	
	

	public MemberAccountEntity() {
		super();
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}



	public long getMemberId() {
		return memberId;
	}



	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}



	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		
		return json;
	}
}
