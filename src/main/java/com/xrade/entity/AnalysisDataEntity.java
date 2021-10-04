package com.xrade.entity;

import org.json.JSONObject;

import com.xrade.models.analysis.AnalysisData;

/**
 * 
 * @author Software
 *
 *         Analyse data enity
 */
public class AnalysisDataEntity extends AnalysisData implements EntityInterface {

	private long id;
	/**
	 * Second primary key
	 * 
	 * It's a concatenation of the id and a simple phrase
	 */
	private String identifier;

	public AnalysisDataEntity() {
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

	public JSONObject toJson() {

		JSONObject json = new JSONObject();

		json.put("id", this.getId());
		json.put("position", this.getPosition());
		json.put("stop_loss", this.getStopLoss());
		json.put("take_profit", this.getTakeProfit());
		json.put("start_time", this.getStartTime());
		json.put("end_time", this.getEndTime());
		json.put("market", this.getMarket());
		json.put("timeframe", this.getTimeframe());
		json.put("order_type", this.getOrder());
		json.put("capital", this.getCapital());
		json.put("profit", this.getProfit());
		json.put("loss", this.getLoss());

		return json;
	}
	
	/**
	 * Transform object to a json format
	 * 
	 * @param analysisData
	 * @return
	 */
	public static JSONObject toJson(AnalysisData analysisData) {
		JSONObject json = new JSONObject();

		//json.put("id", obj.getId());
		json.put("position", analysisData.getPosition());
		json.put("stop_loss", analysisData.getStopLoss());
		json.put("take_profit", analysisData.getTakeProfit());
		json.put("start_time", analysisData.getStartTime());
		json.put("end_time", analysisData.getEndTime());
		json.put("market", analysisData.getMarket());
		json.put("timeframe", analysisData.getTimeframe());
		json.put("order_type", analysisData.getOrder());
		json.put("capital", analysisData.getCapital());
		json.put("profit", analysisData.getProfit());
		json.put("loss", analysisData.getLoss());

		return json;
	}
}
