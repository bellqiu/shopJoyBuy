package com.spshop.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.Logger;

import com.spshop.model.Country;
import com.spshop.service.factory.ServiceFactory;
import com.spshop.service.intf.CountryService;

public class EmailTools {
    private final static Properties emailProperties = new Properties();
    private final static Properties commonEmailProperties = new Properties();
    private static Logger logger = Logger.getLogger(EmailTools.class);
    static {
        try {
            emailProperties.load(EmailTools.class.getResourceAsStream("/emailConfig.properties"));
            commonEmailProperties.load(EmailTools.class.getResourceAsStream("/mailConf.properties"));
        } catch (IOException e) {
        	logger.error(e.getMessage(), e);
            //e.printStackTrace();
        }
    }

    public static void sendRegisterEmail(String sendTo) {
        SimpleEmail email = parseRegisterEmail();
        try {
            email.addTo(sendTo);
            email.setCharset(Constants.DEFAULT_MAIL_CHARSET);
            email.send();
        } catch (EmailException e) {
            //e.printStackTrace();
        	logger.error(e.getMessage(), e);
        }
    }
    
    public static void sendRecoveryEmail(String sendTo){
        SimpleEmail email = parseRecoveryEmail();
        try {
            email.addTo(sendTo);
            email.setCharset(Constants.DEFAULT_MAIL_CHARSET);
            email.send();
        } catch (EmailException e) {
            //e.printStackTrace();
        	logger.error(e.getMessage(), e);
        }
    }

    private static SimpleEmail parseRegisterEmail() {
        SimpleEmail email = new SimpleEmail();
        try {
            if (emailProperties.containsKey(Constants.REGISTER_MAIL_HOST_NAME)) {
                email.setHostName(emailProperties.getProperty(Constants.REGISTER_MAIL_HOST_NAME));
            } else {
                email.setHostName(Constants.DEFAULT_MAIL_HOST_NAME);
            }
            if (emailProperties.containsKey(Constants.REGISTER_MAIL_FROM_ACCOUNT)
                && emailProperties.containsKey(Constants.REGISTER_MAIL_FROM_PASSWORD)) {
                email.setAuthentication(emailProperties.getProperty(Constants.REGISTER_MAIL_FROM_ACCOUNT),
                                        emailProperties.getProperty(Constants.REGISTER_MAIL_FROM_PASSWORD));
                email.setFrom(emailProperties.getProperty(Constants.REGISTER_MAIL_FROM_ACCOUNT));
            } else {
                email.setAuthentication(Constants.DEFAULT_MAIL_FROM_ACCOUNT, Constants.DEFAULT_MAIL_FROM_PASSWORD);
            }
            if (emailProperties.containsKey(Constants.REGISTER_MAIL_TITLE)) {
                email.setSubject(emailProperties.getProperty(Constants.REGISTER_MAIL_TITLE));
            } else {
                email.setSubject(Constants.DEFAULT_REGISTER_MAIL_TITLE);
            }
            if (emailProperties.containsKey(Constants.REGISTER_MAIL_CONTENT)) {
                email.setMsg(emailProperties.getProperty(Constants.REGISTER_MAIL_CONTENT));
            } else {
                email.setMsg(Constants.DEFAULT_REGISTER_MAIL_CONTENT);
            }
        } catch (EmailException e) {
        	logger.error(e.getMessage(), e);
           // e.printStackTrace();
        }

        email.setTLS(true);
        return email;
    }
    
    private static SimpleEmail parseRecoveryEmail() {
        SimpleEmail email = new SimpleEmail();
        try {
            if (emailProperties.containsKey(Constants.RECOVERY_MAIL_HOST_NAME)) {
                email.setHostName(emailProperties.getProperty(Constants.RECOVERY_MAIL_HOST_NAME));
            } else {
                email.setHostName(Constants.DEFAULT_MAIL_HOST_NAME);
            }
            if (emailProperties.containsKey(Constants.RECOVERY_MAIL_FROM_ACCOUNT)
                && emailProperties.containsKey(Constants.RECOVERY_MAIL_FROM_PASSWORD)) {
                email.setAuthentication(emailProperties.getProperty(Constants.RECOVERY_MAIL_FROM_ACCOUNT),
                                        emailProperties.getProperty(Constants.RECOVERY_MAIL_FROM_PASSWORD));
                email.setFrom(emailProperties.getProperty(Constants.RECOVERY_MAIL_FROM_ACCOUNT));
            } else {
                email.setAuthentication(Constants.DEFAULT_MAIL_FROM_ACCOUNT, Constants.DEFAULT_MAIL_FROM_PASSWORD);
            }
            if (emailProperties.containsKey(Constants.RECOVERY_MAIL_TITLE)) {
                email.setSubject(emailProperties.getProperty(Constants.RECOVERY_MAIL_TITLE));
            } else {
                email.setSubject(Constants.DEFAULT_RECOVERY_MAIL_TITLE);
            }
            if (emailProperties.containsKey(Constants.RECOVERY_MAIL_CONTENT)) {
                email.setMsg(emailProperties.getProperty(Constants.RECOVERY_MAIL_CONTENT));
            } else {
                email.setMsg(Constants.DEFAULT_RECOVERY_MAIL_CONTENT);
            }
        } catch (EmailException e) {
        	logger.error(e.getMessage(), e);
        	// e.printStackTrace();
        }

        email.setTLS(true);
        return email;
    }
    
    /**
     * 
     * @param mailType email type from configuration prop
     * @param subject email subject
     * @param variable variable for freeMarker to format mail content
     */
    public static void sendMail(String mailType, String subject, Map<String,Object> variable, String sendTo){
    	String templateType = commonEmailProperties.getProperty(mailType + ".template.type", "");
    	
    	List<Country> countries = ServiceFactory.getService(CountryService.class).getAllCountries();
		
		Map<String, Country> cMap = new HashMap<String,Country>();
		
		for (Iterator iterator = countries.iterator(); iterator.hasNext();) {
			Country country = (Country) iterator.next();
			cMap.put(country.getId()+"", country);
		}
		
		variable.put("countryMap", cMap);
		
    	String mailContent = TempleteParser.parseMailContent(templateType, variable);
    	logger.info("send mail to :" + sendTo);
    	if (mailContent != null) {
    		HtmlEmail   email = new HtmlEmail();
    	    try {
    	        if (commonEmailProperties.containsKey(mailType + Constants.MAIL_HOST_NAME)) {
    	            email.setHostName(commonEmailProperties.getProperty(mailType + Constants.MAIL_HOST_NAME));
    	        } else {
    	            email.setHostName(Constants.DEFAULT_MAIL_HOST_NAME);
    	        }
    	        if (commonEmailProperties.containsKey(mailType + Constants.MAIL_FROM_ACCOUNT)
    	                && commonEmailProperties.containsKey(mailType + Constants.MAIL_FROM_PASSWORD)) {
    	            email.setAuthentication(commonEmailProperties.getProperty(mailType + Constants.MAIL_FROM_ACCOUNT),
    	                                    commonEmailProperties.getProperty(mailType + Constants.MAIL_FROM_PASSWORD));
    	            email.setFrom(commonEmailProperties.getProperty(mailType + Constants.MAIL_FROM_ACCOUNT), Constants.MAIL_FROM_NAME);
    	        } else {
    	            email.setAuthentication(Constants.DEFAULT_MAIL_FROM_ACCOUNT, mailType + Constants.DEFAULT_MAIL_FROM_PASSWORD);
    	        }
    	        email.setSubject(subject);
    	        email.setHtmlMsg(mailContent);
    	        email.setTLS(true);
    	        email.addTo(sendTo);
    	        email.setCharset(Constants.DEFAULT_MAIL_CHARSET);
    	        email.send();
    	    } catch (EmailException e) {
    	        logger.error(e.getMessage(), e);
    	    }
        } else {
            logger.error("cannot parse email template");
        }
    }
    
    public static void main(String[] args) {
        List<String> mailList = new ArrayList<String>();
        for (int i = 0; i < 50; i++) {
            mailList.add("kabist@gmail.com");
        }
        for (String email : mailList) {
            EmailTools.sendMail("paid", "paid successful", new HashMap<String, Object>(), email);
        }
    }
}
