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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 
 * @author <link href="wan-shan.zhu@hp.com">Spark Zhu</link>
 * @version 1.0
 */
public class HomeFilter implements Filter{
	
	
	private static final String ENSURE_SCURE_URLS = "securedUrls";
	
	private String[] securedURLs = new String[]{};
	

	@Override
	public void destroy() {
		
	}
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		
		String securedUrls = config.getInitParameter(ENSURE_SCURE_URLS);
		
		if(StringUtils.isNotBlank(securedUrls)){
			securedURLs = securedUrls.trim().split(",");
		}
		
		for (int i = 0 ; i<securedURLs.length; i++) {
			String url = securedURLs[i].trim();
			securedURLs[i] = url;
		}
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		HttpServletRequest httpReq = (HttpServletRequest) request;
		HttpServletResponse httpResp = (HttpServletResponse) response;
		
		 String url = httpReq.getRequestURL().toString();
		 if(null != httpReq.getQueryString()){
			 url = url + "?" + httpReq.getQueryString();
		 }
		 
		 //LOGGER.info("Accessing: "+ url);
		 if(url.matches("(?i)(^http[s]{0,1}://[^w]{3}.*)(.*)") && !url.matches("(?i)(^http[s]{0,1}://[\\d]{1,3}.*)(.*)")){
			 url = url.replaceAll("(?i)(^http[s]{0,1}://)", "http://www.");
			 //LOGGER.info("Redirecting : "+ url);
			 httpResp.sendRedirect(url);
			 return;
		 }
		 
		 secureURL(httpReq, httpResp, url);
		 
        String reqUri = httpReq.getRequestURI().toString();
        if (!reqUri.equals("/admin") && reqUri.matches("^(/)([^/\\.]+)")) {
            httpReq.getRequestDispatcher("/p" + reqUri).forward(httpReq, httpResp);
            return;
        }
		 
		 chain.doFilter(request, response);
		
	}
	
	private void secureURL(HttpServletRequest httpReq, HttpServletResponse httpResp, String url) throws IOException{
		 if(null != securedURLs){
			 for (String securedURL : securedURLs) {
				if(url.matches(securedURL)){
					url = url.replaceAll("(?i)(^http)", "https");
					httpResp.sendRedirect(url);
					return ;
				}
			}
		 }
		 
/*		 if(!alreadySecured && url.matches("(?i)(^https:.*)")){
			 url = url.replaceAll("(?i)(^https)", "http");
			 httpResp.sendRedirect(url);
			 return ;
		 }*/
	}

	
	
	public static void main(String[] args) {
		System.out.println("/sdfsd.txt".matches("^(/)([^/\\.]+)"));
	}

}
