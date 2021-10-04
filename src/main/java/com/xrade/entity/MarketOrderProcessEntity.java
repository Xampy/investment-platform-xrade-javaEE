package com.xrade.entity;

import org.json.JSONObject;

import com.xrade.models.order.MarketOrderProcess;

public class MarketOrderProcessEntity extends MarketOrderProcess implements EntityInterface {
	
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
	 * OneToOne relation to MarketOrderEntity
	 */
	private long marketOrderId;
	
	public MarketOrderProcessEntity(){
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

	public long getMarketOrderId() {
		return marketOrderId;
	}

	public void setMarketOrderId(long marketOrderId) {
		this.marketOrderId = marketOrderId;
	}

	public JSONObject toJson() {
		JSONObject json = new JSONObject();

		json.put("id", this.getId() );
		json.put("last_position", this.getLastPosition() );
		json.put("market_order_id", this.getMarketOrderId() );
		json.put("benefit", this.getBenefit() );
		json.put("status", this.getStatus() );
		json.put("close_date", this.getCloseDate() );
		
		//Add addtional data
		

		return json;
	}

	public JSONObject toJson(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
