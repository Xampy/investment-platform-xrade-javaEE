package com.xrade.models.user.transaction;

public abstract class MemberMoneyTransaction {
	protected long id;
	protected double amount;
	protected double amountBefore;
	protected double amountAfter;
	protected String metadata;
	protected boolean filled;
	protected String createdAt;
	
	
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
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	
	
}
