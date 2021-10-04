package com.xrade.entity;

import org.json.JSONObject;

import com.xrade.models.user.MemberSponsorshipProfitMergeRequest;

public class MemberSponsorshipProfitMergeRequestEntity extends MemberSponsorshipProfitMergeRequest implements EntityInterface {
	
	/**
	 * Primary key
	 * 
	 */
	private long id;
	
	/**
	 * ManyToOne
	 */
	private long memberSponsorshipAccountId;
	
	
	
	
	public long getId() {
		return id;
	}




	public void setId(long id) {
		this.id = id;
	}




	public long getMemberSponsorshipAccountId() {
		return memberSponsorshipAccountId;
	}




	public void setMemberSponsorshipAccountId(long memberSponsorshipAccountId) {
		this.memberSponsorshipAccountId = memberSponsorshipAccountId;
	}

	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		
		json.put("id", this.getId());
		json.put("amount", this.getAmount());
		json.put("status", this.status);
		
		
		return json;
	}
}
