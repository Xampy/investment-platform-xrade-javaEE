package com.xrade.models.order;

import com.xrade.models.analysis.MarketAnalysis;

public class MarketOrder {
	
	protected OrderType type;
	protected int lot;
	protected int stopLoss;
	protected int takeProfit;
	protected double position;
	protected int amount;
	protected String createdAt = "";
	
	/**
	 * ManyToOne relation to Market Analysis
	 */
	protected MarketAnalysis marketAnalysis;
	
	/**
	 * OneToOne relation to MarketOrderProcess 
	 */
	protected MarketOrderProcess process;

	public OrderType getType() {
		return type;
	}

	public void setType(OrderType type) {
		this.type = type;
	}

	public int getLot() {
		return lot;
	}

	public void setLot(int lot) {
		this.lot = lot;
	}

	public int getStopLoss() {
		return stopLoss;
	}

	public void setStopLoss(int stopLoss) {
		this.stopLoss = stopLoss;
	}

	public int getTakeProfit() {
		return takeProfit;
	}

	public void setTakeProfit(int takeProfit) {
		this.takeProfit = takeProfit;
	}

	public double getPosition() {
		return position;
	}

	public void setPosition(double position) {
		this.position = position;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public MarketAnalysis getMarketAnalysis() {
		return marketAnalysis;
	}

	public void setMarketAnalysis(MarketAnalysis marketAnalysisi) {
		this.marketAnalysis = marketAnalysisi;
	}

	public MarketOrderProcess getProcess() {
		return process;
	}

	public void setProcess(MarketOrderProcess process) {
		this.process = process;
	}
	
	
	
	
}
