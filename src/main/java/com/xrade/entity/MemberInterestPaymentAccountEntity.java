package com.xrade.entity;

import org.json.JSONObject;

import com.xrade.models.user.MemberInterestPaymentAccount;

/**
 * Class MemberAccountInterestPaymentAccountEntity
 * Hold the database base data
 * 
 * @author Software
 *
 */
public class MemberInterestPaymentAccountEntity extends MemberInterestPaymentAccount implements EntityInterface {
	
	
	/**
	 * Primary key
	 * 
	 */
	private long id;
	
	/**
	 * OneToOne
	 */
	private long memberAccountId;
	
	
	public MemberInterestPaymentAccountEntity() {
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
