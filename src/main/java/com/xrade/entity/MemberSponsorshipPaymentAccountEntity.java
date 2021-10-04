package com.xrade.entity;

import org.json.JSONObject;

import com.xrade.models.user.MemberSponsorshipPaymentAccount;

/**
 * class MemberSponsorshipPaymentAccountEntity holds the member sponsorship
 * accoun as in the database
 * 
 * @author Software
 *
 */
public class MemberSponsorshipPaymentAccountEntity extends MemberSponsorshipPaymentAccount implements EntityInterface {
	/**
	 * Primary key
	 * 
	 */
	private long id;
	
	/**
	 * OneToOne
	 */
	private long memberAccountId;
	
	
	public MemberSponsorshipPaymentAccountEntity() {
		super();
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
		// TODO Auto-generated method stub
		return null;
	}
}
