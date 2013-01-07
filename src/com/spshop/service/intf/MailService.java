/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.spshop.service.intf;
/**
 * 
 * @author <link href="wan-shan.zhu@hp.com">Spark Zhu</link>
 * @version 1.0
 */
public interface MailService {
	boolean sendMain(String to, String subject, String content,String username, String password);
}
