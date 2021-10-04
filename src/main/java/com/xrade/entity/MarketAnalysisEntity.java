package com.xrade.entity;

import org.json.JSONObject;

import com.xrade.models.analysis.MarketAnalysis;

public class MarketAnalysisEntity extends MarketAnalysis implements EntityInterface {
	
	private long id;
	/**
	 * Second primary key
	 * 
	 * It's a concatenation of the id and a simple phrase
	 */
	private String identifier;
	
	/**
	 * OneToOne relation to AnalaysisDataEntity
	 */
	private long analysisDataId;
	
	public MarketAnalysisEntity(){
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

	public long getAnalysisDataId() {
		return analysisDataId;
	}

	public void setAnalysisDataId(long analysisDataId) {
		this.analysisDataId = analysisDataId;
	}
	
	
	
	public JSONObject toJson() {
		
		JSONObject json = new JSONObject();
		
		json.put("id", this.getId());
		json.put("market", this.getMarket());
		json.put("total_lot", this.getTotalLot());
		json.put("available_lot", this.getAvailableLot() );
		json.put("price",this.getPrice());
		json.put("max_profit", this.getMaxProfit());
		json.put("max_loss", this.getMaxLoss());
		json.put("published", this.isPublished());
		
		return json;
	}

	public JSONObject toJson(Object obj) {
		JSONObject json = new JSONObject();
		
		json.put("id", ( (MarketAnalysisEntity) obj ).getId());
		json.put("market", ( (MarketAnalysisEntity) obj ).getMarket());
		json.put("total_lot", ( (MarketAnalysisEntity) obj ).getTotalLot());
		json.put("available_lot", ( (MarketAnalysisEntity) obj ).getAvailableLot() );
		json.put("price", ( (MarketAnalysisEntity) obj ).getPrice());
		json.put("max_profit", ( (MarketAnalysisEntity) obj ).getMaxProfit());
		json.put("max_loss", ( (MarketAnalysisEntity) obj ).getMaxLoss());
		json.put("published", ( (MarketAnalysisEntity) obj ).isPublished());
		
		return json;
	}
	
	
	
}
