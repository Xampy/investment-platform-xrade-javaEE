package com.xrade.service.mail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;

public abstract class AbstractMailSender {
	
	
	protected Session mailSession;
	protected String senderAddress;
	protected String recipientAddress;
	
	public AbstractMailSender(Session mailSession) {
		super();
		this.mailSession = mailSession;
		this.setSenderAddress(MailSession.EMAIL_CONFIG.getUsername());
		
		
	}


	public void send(){
		Message message = this.prepareMessage();
		try {
			mailSession.getTransport();
			Transport.send(message, MailSession.EMAIL_CONFIG.getUsername(), MailSession.EMAIL_CONFIG.getPassword());
			//Transport.send(message, "xamp");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public abstract Message prepareMessage();
	protected abstract String formatContent();


	public String getSenderAddress() {
		return senderAddress;
	}


	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}


	public String getRecipientAddress() {
		return recipientAddress;
	}


	public void setRecipientAddress(String recipientAddress) {
		this.recipientAddress = recipientAddress;
	}
	
	
	
	
	
	
	
	
}
