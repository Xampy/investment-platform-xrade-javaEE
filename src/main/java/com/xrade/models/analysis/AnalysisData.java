package com.xrade.models.analysis;

import com.xrade.models.market.MarketTimeframe;
import com.xrade.models.order.OrderType;

public class AnalysisData {
	
	protected OrderType order;
	protected double position;
	protected double stopLoss;
	protected double takeProfit;
	protected String startTime;
	protected String endTime;
	protected String market;
	protected MarketTimeframe timeframe;
	protected double capital;
	protected double profit;
	protected double loss;
	protected String createdAt = "";
	
	
	
	public AnalysisData(){}

	public OrderType getOrder() {
		return order;
	}

	public void setOrder(OrderType order) {
		this.order = order;
	}

	public double getPosition() {
		return position;
	}

	public void setPosition(double position) {
		this.position = position;
	}

	public double getStopLoss() {
		return stopLoss;
	}

	public void setStopLoss(double stopLoss) {
		this.stopLoss = stopLoss;
	}

	public double getTakeProfit() {
		return takeProfit;
	}

	public void setTakeProfit(double takeProfit) {
		this.takeProfit = takeProfit;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public MarketTimeframe getTimeframe() {
		return timeframe;
	}

	public void setTimeframe(MarketTimeframe timeframe) {
		this.timeframe = timeframe;
	}

	public double getCapital() {
		return capital;
	}

	public void setCapital(double capital) {
		this.capital = capital;
	}

	public double getProfit() {
		return profit;
	}

	public void setProfit(double profit) {
		this.profit = profit;
	}

	public double getLoss() {
		return loss;
	}

	public void setLoss(double loss) {
		this.loss = loss;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	
	
	
}
