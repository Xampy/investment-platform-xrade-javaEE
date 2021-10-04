package com.xrade.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

public class HttpJsonResponse {
	
	private HttpServletResponse response;
	private HashMap<String, Object> payload;
	private JSONObject data;

	HttpJsonResponse(HttpServletResponse response) {
		super();
		this.response = response;
		this.payload = new HashMap<String, Object>();
	}
	
	public static HttpJsonResponse create(HttpServletResponse response){
		return new HttpJsonResponse(response);
	}
	
	public HttpJsonResponse addData(String key, Object value){
		this.payload.put(key, value);
		return this;
	}
	
	public HttpJsonResponse addData(String key,JSONObject value){
		this.payload.put(key, value);
		return this;
	}
	
	public HttpJsonResponse build() {
		this.data = new JSONObject();
		for(String key: this.payload.keySet()){
			data.put(key, this.payload.get(key));
		}
		return this;
	}
	
	public void send() throws IOException{
		PrintWriter res = this.response.getWriter();
		res.write(data.toString());
		res.close();
	}

	public void send(JSONArray data) throws IOException {
		PrintWriter res = this.response.getWriter();
		res.write(data.toString());
		res.close();
		
	}
	
	public void send(JSONObject data) throws IOException {
		PrintWriter res = this.response.getWriter();
		res.write(data.toString());
		res.close();
		
	}

	public void send(String data) throws IOException {
		PrintWriter res = this.response.getWriter();
		res.write(data);
		res.close();
		
	}
	
}
