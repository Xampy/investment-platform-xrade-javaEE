package com.xrade.models.user;

public class MemberAccount {
	protected double amount = 0.0f;
	protected String transacrionCode = "";
	
	
	
	public MemberAccount() {
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getTransacrionCode() {
		return transacrionCode;
	}
	public void setTransacrionCode(String transacrionCode) {
		this.transacrionCode = transacrionCode;
	}
}
