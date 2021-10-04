package com.xrade.models.user;

public class MemberInvestmentProfitWithdrawalRequest {
	
	protected String payment = "";
	protected double amount = 0.0f;
	protected double amountBefore = 0.0f;
	protected double amountAfter = 0.0f;
	protected String metadata = "";
	protected boolean filled = false;
	protected String createdAt = "";
	
	
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
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
	public String getMetadata() {
		return metadata;
	}
	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}
	public boolean isFilled() {
		return filled;
	}
	public void setFilled(boolean filled) {
		this.filled = filled;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	
	
	
}
