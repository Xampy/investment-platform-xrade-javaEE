package com.xrade.entity;

import org.json.JSONObject;

import com.xrade.models.order.MarketOrder;

public class MarketOrderEntity extends MarketOrder implements EntityInterface {

	/**
	 * PrimaryKey
	 */
	private long id;

	/**
	 * Second primary key
	 * 
	 * It's a concatenation of the id and a simple phrase
	 */
	private String identifier;

	/**
	 * ManyToOne relation to MarketAnalaysisEntity
	 */
	private long marketAnalysisId;

	/**
	 * ManyToOne relation to MemberEntity
	 */
	private long memberId;

	public MarketOrderEntity() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public long getMarketAnalysisId() {
		return marketAnalysisId;
	}

	public void setMarketAnalysisId(long marketAnalysisId) {
		this.marketAnalysisId = marketAnalysisId;
	}

	public long getMemberId() {
		return memberId;
	}

	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}

	public JSONObject toJson() {
		JSONObject json = new JSONObject();

		json.put("id", this.getId() );
		json.put("position", this.getPosition() );
		json.put("lot", this.getLot() );
		json.put("amount", this.getAmount() );
		json.put("created_at", this.getCreatedAt() );
		json.put("stop_loss", this.getStopLoss() );
		json.put("take_profit", this.getTakeProfit() );
		json.put("stop_loss", this.getStopLoss() );
		
		//Add member and analysis
		

		return json;
	}

	public JSONObject toJson(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

}
