package com.xrade.validator.factory;

import com.xrade.validator.form.BackOfficerLoginFormValidator;
import com.xrade.validator.form.SaveAnalysisDataFormValidator;
import com.xrade.validator.form.SaveMarketAnanlysisFormValidator;
import com.xrade.validator.form.fund.PerfectMoneyPaymentStatusFormValidator;
import com.xrade.validator.form.fund.UpdateDepositRequestFormValidator;

public class FormValidatorFactory {
	
	public static SaveMarketAnanlysisFormValidator getSaveMarketAnanlysisFormValidato(){
		return new SaveMarketAnanlysisFormValidator();
	}
	
	public static SaveAnalysisDataFormValidator getSaveAnalysisDataFormValidator(){
		return new SaveAnalysisDataFormValidator();
	}
	
	public static BackOfficerLoginFormValidator getBackOfficerLoginFormValidator(){
		return new BackOfficerLoginFormValidator();
	}
	
	public static UpdateDepositRequestFormValidator getUpdateDepositRequestFormValidator(){
		return new UpdateDepositRequestFormValidator();
	}
	
	
	public static PerfectMoneyPaymentStatusFormValidator getPerfectMoneyPaymentStatusFormValidator(){
		return new PerfectMoneyPaymentStatusFormValidator();
	}

}
