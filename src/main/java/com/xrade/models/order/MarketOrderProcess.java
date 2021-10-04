package com.xrade.models.order;

public class MarketOrderProcess {
	
	protected double lastPosition;
	protected String status;
	protected String closeDate;
	protected int benefit;
	
	
	
	public double getLastPosition() {
		return lastPosition;
	}
	public void setLastPosition(double lastPosition) {
		this.lastPosition = lastPosition;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCloseDate() {
		return closeDate;
	}
	public void setCloseDate(String closeDate) {
		this.closeDate = closeDate;
	}
	public int getBenefit() {
		return benefit;
	}
	public void setBenefit(int benefit) {
		this.benefit = benefit;
	}
	
	
	
}
