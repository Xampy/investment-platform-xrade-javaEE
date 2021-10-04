package com.xrade.models.user;

/**
 * 
 * Class MemberAccountInterestPaymentAccount  to hold information
 * about all interest payment made for the account
 * 
 * @author Software
 *
 */
public class MemberInterestPaymentAccount {
	
	private double amount = 0.0f;
	private String createdAt = "";
	
	public MemberInterestPaymentAccount() {
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
