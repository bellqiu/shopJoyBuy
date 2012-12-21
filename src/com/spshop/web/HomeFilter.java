/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.spshop.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * 
 * @author <link href="wan-shan.zhu@hp.com">Spark Zhu</link>
 * @version 1.0
 */
public class HomeFilter implements Filter{
	
	private static final Logger LOGGER = Logger.getLogger(HomeFilter.class);

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		HttpServletRequest httpReq = (HttpServletRequest) request;
		HttpServletResponse httpResp = (HttpServletResponse) response;
		
		 String url = httpReq.getRequestURL().toString();
		 LOGGER.info("Accessing: "+ url);
		 if(url.matches("(?i)(^http://[^w]{3}.*)(.*)") && !url.matches("(?i)(^http://[\\d]{1,3}.*)(.*)")  ){
			 url = url.replaceAll("(?i)(^http://)", "http://www.");
			 if(null != httpReq.getQueryString()){
				 url = url + "?" + httpReq.getQueryString();
			 }
			 LOGGER.info("Redirecting : "+ url);
			 httpResp.sendRedirect(url);
			 return;
		 }
		
		 chain.doFilter(request, response);
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		System.out.println("http://joybuy.co.uk/".replaceAll("(?i)(^http://)", "http://www."));
	}

}
