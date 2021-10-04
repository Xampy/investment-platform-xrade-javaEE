package com.xrade.service.perfectMoney;

import com.xrade.forms.PerfectMoneyPaymentStatusForm;
import com.xrade.security.XStringHasher;

public class PerfectMoneyService {
	
	
	private static final String ALTERNATE_PASSPHRASE = "v9GU6Q9z0mC0PicWgCPNyoCiq";

	public String generateV2Hash(PerfectMoneyPaymentStatusForm form){
		
		String originalString = "" 
				+ form.getPaymentId() + ":"
				+ form.getPayeeAccount() + ":"
				+ form.getPaymentAmount() + ":"
				+ form.getPaymentUnits() + ":"
				+ form.getPaymentBatchNum() + ":"
				+ form.getPayerAccount() + ":"
				+ XStringHasher.hashMD5(ALTERNATE_PASSPHRASE) + ":"
				+ form.getTimestampgmt();
		
		return XStringHasher.hashMD5(originalString);
	}
	
}
