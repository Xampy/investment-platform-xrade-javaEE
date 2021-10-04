package com.xrade.service.mail.sender;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.xrade.service.mail.AbstractMailSender;

public class EmailConfirmationMailSender extends AbstractMailSender {
	
	
	private String code = "369668";

	public EmailConfirmationMailSender(Session mailSession) {
		super(mailSession);
	}

	@Override
	public Message prepareMessage() {
		try {
			
            Message message = new MimeMessage(this.mailSession);
            
            message.setFrom(new InternetAddress(this.senderAddress));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(this.recipientAddress));
            message.setSubject("Welcome to BlueShip Invest system!");
            String htmlCode =  this.formatContent();
            message.setContent(htmlCode, "text/html");
            
            return message;
        } catch (Exception ex) {
            Logger.getLogger(EmailConfirmationMailSender.class.getName()).log(Level.SEVERE, null, ex);
        }
		return null;
	}
	
	
	@Override
	protected String formatContent(){
		String messageTemplate = " "
		+ "<p>Dear New BlueShip Member,<p>"
		+ "<p>You have completed the registration in BlueShip system.</p>"
		+ "<p>Your verification code : <strong>" + this.code + "</strong></p>"
        + "<p>Thank you for registering with BlueShip!</p>";
		
		
		return messageTemplate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
	
	

}
