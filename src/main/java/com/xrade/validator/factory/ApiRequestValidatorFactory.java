package com.xrade.validator.factory;

import com.xrade.validator.apiRequest.SaveAnalysisDataRequestValidator;
import com.xrade.validator.apiRequest.SaveMarketAnalysisRequestValidator;
import com.xrade.validator.apiRequest.SaveMarketOrderProcessRequestValidator;
import com.xrade.validator.apiRequest.SaveMarketOrderRequestValidator;
import com.xrade.validator.apiRequest.UpdateMarketOrderProcessValidator;
import com.xrade.validator.apiRequest.fund.MakeDepositRequestValidator;
import com.xrade.validator.apiRequest.fund.MakeWithdrawRequestValidator;
import com.xrade.validator.apiRequest.fund.investment.MakeInvestmentProfitMergeRequestValidator;
import com.xrade.validator.apiRequest.fund.investment.MakeInvestmentProfitWithdrawRequestValidator;
import com.xrade.validator.apiRequest.fund.sponsorship.MakeSponsorshipProfitMergeRequestValidator;
import com.xrade.validator.apiRequest.fund.sponsorship.MakeSponsorshipProfitWithdrawRequestValidator;
import com.xrade.validator.apiRequest.member.LoginMemberRequestValidator;
import com.xrade.validator.apiRequest.member.RegisterMemberRequestValidator;

/**
 * 
 * @author Software
 *
 * API request validator factory. Get a validor
 * on api request by name
 * 
 * We'll be rewritten
 *
 */
public class ApiRequestValidatorFactory {
	
	
	public static SaveAnalysisDataRequestValidator getSaveAnalystDataValidator() {
		return new SaveAnalysisDataRequestValidator();
	}
	
	public static SaveMarketAnalysisRequestValidator getSaveMarketAnalysisRequestValidator(){
		return new SaveMarketAnalysisRequestValidator();
	}
	
	public static SaveMarketOrderRequestValidator getSaveMarketOrderRequestValidato(){
		return new SaveMarketOrderRequestValidator();
	}
	
	public static SaveMarketOrderProcessRequestValidator getSaveMarketOrderProcessRequestValidator(){
		return new SaveMarketOrderProcessRequestValidator();
	}
	
	public static UpdateMarketOrderProcessValidator getUpdateMarketOrderProcessValidator(){
		return new UpdateMarketOrderProcessValidator();
	}
	
	public static RegisterMemberRequestValidator getRegisterMemberRequestValidator(){
		return new RegisterMemberRequestValidator();
	}
	
	public static LoginMemberRequestValidator getLoginMemberRequestValidator(){
		return new LoginMemberRequestValidator();
	}
	
	public static MakeDepositRequestValidator getMakeDepositRequestValidator(){
		return new MakeDepositRequestValidator();
	}
	
	public static MakeWithdrawRequestValidator getMakeWithdrawRequestValidator(){
		return new MakeWithdrawRequestValidator();
	}
	
	
	//Investment profit
	public static MakeInvestmentProfitWithdrawRequestValidator getMakeInvestmentProfitWithdrawRequestValidator(){
		return new MakeInvestmentProfitWithdrawRequestValidator();
	}
	
	public static MakeInvestmentProfitMergeRequestValidator getMakeInvestmentProfitMergeRequestValidator(){
		return new MakeInvestmentProfitMergeRequestValidator();
	}
	
	
	//Sponsorship profit
	public static MakeSponsorshipProfitWithdrawRequestValidator getMakeSponsorshipProfitWithdrawRequestValidator(){
		return new MakeSponsorshipProfitWithdrawRequestValidator();
	}
	
	public static MakeSponsorshipProfitMergeRequestValidator getMakeSponsorshipProfitMergeRequestValidator(){
		return new MakeSponsorshipProfitMergeRequestValidator();
	}
	
}
