/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.spshop.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.spshop.cache.SCacheFacade;
import com.spshop.model.Product;
import com.spshop.utils.Constants;
import com.spshop.web.view.PageView;

/**
 * 
 * @author <link href="wan-shan.zhu@hp.com">Spark Zhu</link>
 * @version 1.0
 */
public class ProductDataInterceptor extends AbstractCategoryDataInterceptor{
	
	private static final Logger logger = Logger.getLogger(ProductDataInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
			PageView pageView = new PageView();
	        
	        String requestURI = request.getRequestURI();
	        
	        logger.debug("requestURI: " + requestURI);
	        
	        String name = requestURI.substring(requestURI.lastIndexOf('/'));
	        
	        if (pageView.getCategory() == null) {
	            Product product = SCacheFacade.getProduct(name.replace("/", ""));
	            if (product != null && CollectionUtils.isNotEmpty(product.getCategories())) {
	                pageView.setCategory(product.getCategories().get(product.getCategories().size()-1));
	            }
	        }
	        
	        populateBreadcrumbForPage(pageView.getCategory(), pageView.getBreadcrumb());
	        
	        request.setAttribute(Constants.PAGE_VIEW, pageView);
	        
	       return true;
		
	}
	
}
