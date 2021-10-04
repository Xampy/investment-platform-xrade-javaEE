package com.xrade.models.user;

import com.xrade.web.AppConfig;

public class MemberAccountInterestPayment {
	
	protected double rate = AppConfig.INTEREST_RATE;
	protected double amountBefore = 0.0f;
	protected double amountAfter = 0.0f;
	protected String createdAt = "";
	
	public MemberAccountInterestPayment() {
		super();
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
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
