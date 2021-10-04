package com.xrade.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class HttpJsonResponseBuilder {

	private final HttpServletResponse response;
	private HashMap<String, JSONObject> payload = null; 

	public HttpJsonResponseBuilder(HttpServletResponse response) {
		super();
		this.response = response;
		this.response.setContentType("application/json");
		this.response.setCharacterEncoding("utf-8");
		this.payload = new HashMap<String, JSONObject>();
	}
	
	public JSONObject build() {
		//Construct response as jsonObject
		JSONObject data = new JSONObject();
		for(String key: this.payload.keySet()){
			String name = JSONObject.getNames(this.payload.get(key))[0];
			data.put(name, this.payload.get(key).get(name) );
		}
		return data;
	}
	
	public  HttpJsonResponseBuilder setStatus(int status){
		this.response.setStatus(status);
		return this;
	}
	
	public HttpJsonResponseBuilder addData(String key, JSONObject data){
		this.payload.put(key, data);
		return this;
	}
	
	public void addData(String key, Object data){
		this.payload.put(key,  new JSONObject().put(key, data) );
	}
	
	public void reset(){
		this.payload.clear();
	}
	
	public void send() throws IOException{		
		PrintWriter res = this.response.getWriter();
		res.write(this.build().toString());
		res.close();
	}
	
	public void send(JSONObject data) throws IOException{		
		PrintWriter res = this.response.getWriter();
		res.write(data.toString());
		res.close();
	}
	
	
}
