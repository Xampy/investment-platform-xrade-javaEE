package com.xrade.models.user.transaction;

public class MemberCardTransaction extends MemberMoneyTransaction {
	
	protected String paymentSessionId = null;
	protected String identifier = null;

	

	public String getPaymentSessionId() {
		return paymentSessionId;
	}

	public void setPaymentSessionId(String paymentSessionId) {
		this.paymentSessionId = paymentSessionId;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	
}
