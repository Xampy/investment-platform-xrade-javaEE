package com.xrade.validator;

import org.json.JSONObject;

/**
 * 
 * @author Software
 *
 * Validate a request. Controlling field submitted
 * Chek field existence and return the needed obuject
 *
 * @param <T>
 */
public abstract class AbstractRequestValidator<T> {
	/**
	 * Validate a string params request
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	//public abstract T validate(HttpServletRequest request) throws Exception;
	
	/**
	 * Validate a json request
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public abstract T validate(JSONObject request) throws Exception;
	
	/**
	 * Validate T object
	 * @param obj
	 * @return
	 */
	public abstract T validate(T obj);
	
	
	/**
	 * 
	 * If it's a request we subtr the 
	 * request value by fiedname
	 * 
	 * @param field
	 * @param request
	 * @return
	 */
	protected String getFieldValue(String field, JSONObject request){
		String value = request.get(field).toString();
		if ( value == null || value.trim().length() == 0 ) {
			return null;
		} else {
			return value.trim();
		}
	}
}
