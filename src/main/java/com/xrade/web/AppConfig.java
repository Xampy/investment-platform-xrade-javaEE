package com.xrade.web;

public class AppConfig {
	
	/**
	 * The base amount on each can not be 
	 * less than the value defined below 
	 */
	public static final int BASE_AMOUNT = 10;
	public static final int BASE_WITHDRAWAL_AMOUNT = 10;
	
	public static final int BASE_SPONSORSHIP_WITHDRAWAL_AMOUNT = 5;
	
	/**
	 * Percent persued the member
	 * on their base account after 
	 * period eleapsed
	 */
	public static final double INTEREST_RATE = 0.01f;
	
	public static final double SPONSORSHIP_COMMISSION_RATE = 0.03f;
	public static final double SPONSORSHIP_BASE_MERGE_AMOUNT = 10;
	
	public static final long BASE_TRANSACTION_NUMBER = 1235L;
}
