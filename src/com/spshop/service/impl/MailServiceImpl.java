/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.spshop.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.spshop.service.factory.ServiceFactory;
import com.spshop.service.intf.MailService;

/**
 * 
 * @author <link href="wan-shan.zhu@hp.com">Spark Zhu</link>
 * @version 1.0
 */
public class MailServiceImpl extends org.springframework.mail.javamail.JavaMailSenderImpl implements MailService{
	
	private static Logger logger = Logger.getLogger(MailServiceImpl.class);

	@Override
	public boolean sendMain(String to, String subject, String content,String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
		
		MimeMessage message = this.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
			helper.setTo(to);
			helper.setFrom("sss@dd.com");
			helper.setSubject(subject);
			helper.setText(content, true);
			this.send(message);
		} catch (MessagingException e) {
			logger.error(e);
			return false;
		}
		
		return true;
		
	}
	
	public static void main(String[] args) {
		ServiceFactory.getService(MailService.class).sendMain("wan-shan.zhu@hp.com", "test", "<h1>hello</h1>", "service@joybuy.co.uk", "JB20121219!@#A");
	}
}
