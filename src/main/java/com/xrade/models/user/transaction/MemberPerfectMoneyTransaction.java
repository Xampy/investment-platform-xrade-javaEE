package com.xrade.models.user.transaction;

public class MemberPerfectMoneyTransaction extends MemberMoneyTransaction {
	
	protected String identifier = null;
	protected String paymentId = null;
	
	

	
	
	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

}
