package com.xrade.service.mail.factory;

import javax.mail.Session;

import com.xrade.service.mail.MailSession;
import com.xrade.service.mail.sender.DepositRequestMailSender;
import com.xrade.service.mail.sender.EmailConfirmationMailSender;

public class MailSenderFactory {
	
	private static Session mailSession = MailSession.getInstance();
	
	
	public static EmailConfirmationMailSender getEmailConfirmationMailSender(){
		return new EmailConfirmationMailSender(mailSession);
	}
	
	public static DepositRequestMailSender getDepositRequestMailSender(){
		return new DepositRequestMailSender(mailSession);
	}
	
	
}
