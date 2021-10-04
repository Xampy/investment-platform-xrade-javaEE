package com.xrade.service.mail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.naming.Context;
import javax.naming.InitialContext;

import com.xrade.web.GlobalEmailConfig;

public class MailSession {
	
	private static Session session;
	public static GlobalEmailConfig EMAIL_CONFIG;
	
	
	MailSession(){;
		
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			session = (Session) envCtx.lookup("mail/blueshipSession");
			MailSession.EMAIL_CONFIG = (GlobalEmailConfig) envCtx.lookup("bean/GlobalEmailConfig");

		} catch (Exception ex) {
			System.out.println("Error on email");
			System.out.println( ex.getMessage());
			
			throw new RuntimeException(ex);
		}
	}
	
	public static Session getInstance(){
		
		if(MailSession.session == null){
			new MailSession();
		}
		
		return MailSession.session;
	}
}
