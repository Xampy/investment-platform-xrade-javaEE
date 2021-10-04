package com.xrade.entity;

import org.json.JSONObject;

import com.xrade.models.user.Member;

public class MemberEntity extends Member  implements EntityInterface {
	
	/**
	 * Primary key
	 * 
	 */
	private long id;
	
	private MemberInterestPaymentAccountEntity interestAccount = null;
	private MemberSponsorshipPaymentAccountEntity sponsorshipAccount = null;
	
	public MemberEntity(){
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	
	public MemberInterestPaymentAccountEntity getInterestAccount() {
		return interestAccount;
	}

	public void setInterestAccount(MemberInterestPaymentAccountEntity interestAccount) {
		this.interestAccount = interestAccount;
	}
	
	public MemberSponsorshipPaymentAccountEntity getSponsorshipAccount() {
		return sponsorshipAccount;
	}

	public void setSponsorshipAccount(MemberSponsorshipPaymentAccountEntity sponsorshipAccount) {
		this.sponsorshipAccount = sponsorshipAccount;
	}

	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		
		json.put("id", this.getId());
		json.put("lastname", this.getLastname());
		json.put("firstname", this.getFirstname());
		json.put("email", this.getEmail());
		json.put("phone", this.getPhone());
		json.put("point", this.getPoint());
		json.put("created_at", this.getCreatedAt());
		json.put("verified", this.isVerified());
		json.put("grade", this.getGrade());
		json.put("reference", this.getReference());
		json.put("referenced", this.getReferenced());
		json.put("password", this.getPassword());
		
		
		JSONObject account = new JSONObject();
		account.put("amount", this.getAccount().getAmount());
		account.put("transaction_code", this.getAccount().getTransacrionCode());
		
		json.put("account", account);
		if(this.getInterestAccount() != null){
			JSONObject interestAccount = new JSONObject();
			interestAccount.put("amount", this.getInterestAccount().getAmount() );
			
			json.put("interest_account", interestAccount);
		}
		
		if(this.getSponsorshipAccount() != null){
			JSONObject sponsorshipAccount = new JSONObject();
			sponsorshipAccount.put("amount", this.getSponsorshipAccount().getAmount() );
			
			json.put("sponsorship_account", sponsorshipAccount);
		}
		
		
		
		return json;
	}
	
	
}
