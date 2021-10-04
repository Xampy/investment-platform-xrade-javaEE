package com.xrade.validator.custom;

/**
 * Amount restriction validator
 * 
 * @author Software
 *
 */
public class AmountRestrictionValidator {
	
	/**
	 * The minimal amount wanted as value for the
	 * amount field (exclusive)
	 */
	public static final double GLOBAL_MIN_AMOUNT = 0.0f;
	
	/**
	 * The maximalmal amount wanted as value for the
	 * amount field (inclusive)
	 */
	public static final double GLOBAL_MAX_AMOUNT = 100000.0f;
	
	
	public double validate(double amount) throws Exception{
		if(amount > AmountRestrictionValidator.GLOBAL_MIN_AMOUNT && 
				amount < AmountRestrictionValidator.GLOBAL_MAX_AMOUNT){
			return amount;
		}else{
			throw new Exception("Amount interval not respected. The amount need to be strictly positive and the maximal value is 100 000 USD");
		}
	}
	
}
