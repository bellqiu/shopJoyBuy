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

/**
 * 
 * @author <link href="wan-shan.zhu@hp.com">Spark Zhu</link>
 * @version 1.0
 */
public class HomeFilter implements Filter{

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		try{
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		HttpServletRequest httpReq = (HttpServletRequest) request;
		
		String serverName = httpReq.getServerName();
		
		if(!serverName.matches("www\\.\\w+.*") 
				&& !serverName.matches("\\d+\\.\\d+.*")
				&& !serverName.matches("localhost.*")){
			HttpServletResponse httpResp = (HttpServletResponse) response;
			String queryString = httpReq.getQueryString();
			String requestUri = httpReq.getRequestURI();
			httpResp.sendRedirect(request.getScheme()+":www."+request.getServerName() + "/" + (requestUri.equals("/")?"":requestUri) + ((null == queryString) ? "" : "?"+queryString));
			return;
		}
		
		}finally{
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
