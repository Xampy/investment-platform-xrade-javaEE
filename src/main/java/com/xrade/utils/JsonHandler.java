package com.xrade.utils;

import java.io.BufferedReader;
import java.io.IOException;

import org.json.JSONObject;

public class JsonHandler {
	
	public static JSONObject toJSON(BufferedReader reader) throws IOException{
		String data = "";   
	    StringBuilder builder = new StringBuilder();
	    
	    String line;
	    try {
			while ((line = reader.readLine()) != null) {
			    builder.append(line);
			}
			data = builder.toString();
			JSONObject object = new JSONObject(data); 
			 
			 return object;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}
}
