package com.xrade.models.user;

public class MemberWithdrawRequest {
	protected String payment;
	private String accountType; 
	protected double amount;
	protected double amountBefore;
	protected double amountAfter;
	protected String metadata;
	protected boolean filled;
	protected String date;
	protected String extra; //will be not include in the json format for the entity
	
	protected MemberAccount account;
	
	

	public MemberWithdrawRequest() {
		super();
	}



	public String getPayment() {
		return payment;
	}



	public void setPayment(String payment) {
		this.payment = payment;
	}



	public String getAccountType() {
		return accountType;
	}



	public void setAccountType(String accountType) {
		this.accountType = accountType;
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



	public String getDate() {
		return date;
	}



	public void setDate(String date) {
		this.date = date;
	}



	public MemberAccount getAccount() {
		return account;
	}



	public void setAccount(MemberAccount account) {
		this.account = account;
	}



	public String getExtra() {
		return extra;
	}



	public void setExtra(String extra) {
		this.extra = extra;
	}



	

	
	
	
}
