package com.xrade.models.user;

public class MemberSponsorshipProfitMergeRequest {
	protected double amount = 0.0f;
	protected double amountBefore = 0.0f;
	protected double amountAfter = 0.0f;
	protected String status = "";
	protected String createdAt = "";
	
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getAmountBefore() {
		return amountBefore;
	}
	public void setAmountBefore(double amountBefore) {
		this.amountBefore = amountBefore;
	}
	public double getAmountAfter() {
		return amountAfter;
	}
	public void setAmountAfter(double amountAfter) {
		this.amountAfter = amountAfter;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
}
