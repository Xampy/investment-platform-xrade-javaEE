package com.xrade.forms;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.xrade.models.analysis.AnalysisData;

public abstract class DefaultForm<T> {
	
	protected String result;
	protected HashMap<String, String> errors = new HashMap<String, String>();
	
	protected T data = null;
	
	
	
	/**
	 * Handle the request and match the form fields with
	 * the AnalyseData object @see AnalyseData
	 * 
	 * @param request http request got from the servlet
	 * @return object
	 * @throws Exception 
	 */
	public abstract void handleRequest(HttpServletRequest request) throws Exception;
	
	
	
	
	public T getData() {
		return data;
	}




	public String getResult() {
		return result;
	}
	
	/**
	 * Add error message to erros containe
	 * 
	 * @param field the name the input
	 * @param message 
	 */
	protected void setError(String field, String message){
		this.errors.put(field, message);
	}
	public HashMap<String, String> getErrors() {
		return errors;
	}
}
