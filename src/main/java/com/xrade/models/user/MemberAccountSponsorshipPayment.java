package com.xrade.models.user;



public class MemberAccountSponsorshipPayment {
	
	protected double amountBefore = 0.0f;
	protected double amountAfter = 0.0f;
	protected String createdAt = "";
	
	public MemberAccountSponsorshipPayment() {
		super();
	}
	

	public double getAmountBefore() {
		return amountBefore;
	}

	public void setAmountBefore(double d) {
		this.amountBefore = d;
	}

	public double getAmountAfter() {
		return amountAfter;
	}

	public void setAmountAfter(double amountAfter) {
		this.amountAfter = amountAfter;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
}
