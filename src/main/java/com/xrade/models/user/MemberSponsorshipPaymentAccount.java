package com.xrade.models.user;

/**
 * Class Member sponsorship account. Hold amount due to the member
 * at a sponsorship
 * 
 * @author Software
 *
 */
public class MemberSponsorshipPaymentAccount {
	private double amount = 0.0f;
	private String createdAt = "";
	
	public MemberSponsorshipPaymentAccount() {
		super();
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
}
