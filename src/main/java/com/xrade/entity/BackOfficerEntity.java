package com.xrade.entity;

import org.json.JSONObject;

import com.xrade.models.admin.BackOfficer;

public class BackOfficerEntity extends BackOfficer implements EntityInterface {
	
	/**
	 * Primary key
	 * 
	 */
	private long id;
	
	public BackOfficerEntity(){
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public JSONObject toJson() {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		json.put("level", this.getLevel());
		json.put("email", this.getEmail());
		json.put("password", this.getPassword());
		
		return json;
	}

}
