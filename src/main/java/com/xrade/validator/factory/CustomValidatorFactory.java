package com.xrade.validator.factory;

import com.xrade.validator.custom.AmountRestrictionValidator;
import com.xrade.validator.custom.EmailRestrictionValidator;

public class CustomValidatorFactory {
	
	
	public static AmountRestrictionValidator getAmountRestrictionValidator(){
		return new AmountRestrictionValidator();
	}
	
	public static EmailRestrictionValidator getEmailRestrictionValidator(){
		return new EmailRestrictionValidator();
	}
}
