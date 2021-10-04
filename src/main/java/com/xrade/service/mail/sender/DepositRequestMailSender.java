package com.xrade.service.mail.sender;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.xrade.service.mail.AbstractMailSender;

public class DepositRequestMailSender extends AbstractMailSender{
	
	private double amount = 0.0;
	private String username = null;

	public DepositRequestMailSender(Session mailSession) {
		super(mailSession);
	}

	@Override
	public Message prepareMessage() {
		try {
			
            Message message = new MimeMessage(this.mailSession);
            
            message.setFrom(new InternetAddress(this.senderAddress));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(this.recipientAddress));
            message.setSubject("Account deposit - BlueShip USD");
            String htmlCode =  this.formatContent();
            message.setContent(htmlCode, "text/html");
            
            return message;
        } catch (Exception ex) {
            Logger.getLogger(EmailConfirmationMailSender.class.getName()).log(Level.SEVERE, null, ex);
        }
		return null;
	}

	@Override
	public String formatContent() {
		String messageTemplate = " "
				+ "<strong><p style='font-size: 1.2em;'>Dear BlueShip Member,<p></strong>"
				+ "<p>Dear " + this.username + ",</p>"
				+ "<p>We are glad to inform you that your deposit has been successfully processed.</p>"
				+ "<p>The amount  of <strong>$ "+ this.amount  +"</strong> has been credited into your account</p>"
		        + "<p>Regards, BlueShip</p>";
				
				
				return messageTemplate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
	
	
	
	
	
	
	

}
