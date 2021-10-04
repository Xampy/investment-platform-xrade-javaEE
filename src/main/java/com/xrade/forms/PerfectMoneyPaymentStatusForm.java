package com.xrade.forms;

public class PerfectMoneyPaymentStatusForm {
	
	private String payeeAccount = null;
	private String paymentId = null;
	private double paymentAmount;
	private String paymentUnits = null;
	private String paymentBatchNum = null;
	private String payerAccount = null;
	private String timestampgmt = null; 
	private String v2Hash = null;
	
	private String sessionId = null;
	
	
	
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getPayeeAccount() {
		return payeeAccount;
	}
	public void setPayeeAccount(String payeeAccount) {
		this.payeeAccount = payeeAccount;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public double getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public String getPaymentUnits() {
		return paymentUnits;
	}
	public void setPaymentUnits(String paymentUnits) {
		this.paymentUnits = paymentUnits;
	}
	public String getPaymentBatchNum() {
		return paymentBatchNum;
	}
	public void setPaymentBatchNum(String paymentBatchNum) {
		this.paymentBatchNum = paymentBatchNum;
	}
	public String getPayerAccount() {
		return payerAccount;
	}
	public void setPayerAccount(String payerAccount) {
		this.payerAccount = payerAccount;
	}
	public String getTimestampgmt() {
		return timestampgmt;
	}
	public void setTimestampgmt(String timestampgmt) {
		this.timestampgmt = timestampgmt;
	}
	public String getV2Hash() {
		return v2Hash;
	}
	public void setV2Hash(String v2Hash) {
		this.v2Hash = v2Hash;
	}
	
	
	
	
	
}
