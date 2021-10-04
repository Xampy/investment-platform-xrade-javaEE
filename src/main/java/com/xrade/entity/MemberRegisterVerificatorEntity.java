package com.xrade.entity;

import org.json.JSONObject;

import com.xrade.models.user.MemberRegisterVerificator;

public class MemberRegisterVerificatorEntity extends MemberRegisterVerificator implements EntityInterface {
	
	
	/**
	 * Primary key
	 * 
	 */
	private long id;
	
	/**
	 * 
	 */
	private long memberId;
	
	

	public MemberRegisterVerificatorEntity() {
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
		// TODO Auto-generated method stub
		return null;
	}

}
