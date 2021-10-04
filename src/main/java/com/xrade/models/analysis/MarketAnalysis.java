package com.xrade.models.analysis;

public class MarketAnalysis {
	protected String market;
	protected int totalLot;
	protected int availableLot;
	protected int price;
	protected int maxProfit;
	protected int maxLoss;
	protected boolean published = false;
	
	
	protected AnalysisData analysisData;
	
	
	public String getMarket() {
		return market;
	}
	public void setMarket(String market) {
		this.market = market;
	}
	public int getTotalLot() {
		return totalLot;
	}
	public void setTotalLot(int totalLot) {
		this.totalLot = totalLot;
	}
	public int getAvailableLot() {
		return availableLot;
	}
	public void setAvailableLot(int availableLot) {
		this.availableLot = availableLot;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getMaxProfit() {
		return maxProfit;
	}
	public void setMaxProfit(int maxProfit) {
		this.maxProfit = maxProfit;
	}
	public int getMaxLoss() {
		return maxLoss;
	}
	public void setMaxLoss(int maxLoss) {
		this.maxLoss = maxLoss;
	}
	public boolean isPublished() {
		return published;
	}
	public void setPublished(boolean published) {
		this.published = published;
	}
	public AnalysisData getAnalysisData() {
		return analysisData;
	}
	public void setAnalysisData(AnalysisData analysisData) {
		this.analysisData = analysisData;
	}
	
	
}
