/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.spshop.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.spshop.cache.SCacheFacade;
import com.spshop.model.Category;
import com.spshop.model.TabProduct;
import com.spshop.service.factory.ServiceFactory;
import com.spshop.service.intf.CategoryService;
import com.spshop.utils.Constants;
import com.spshop.utils.Utils;
import com.spshop.web.view.PageView;

/**
 * 
 * @author <link href="wan-shan.zhu@hp.com">Spark Zhu</link>
 * @version 1.0
 */
public class CategoryDataInterceptor extends AbstractCategoryDataInterceptor{
	
	private static final Logger logger = Logger.getLogger(CategoryDataInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
			PageView pageView = new PageView();
	        
	        String requestURI = request.getRequestURI();
	        
	        logger.debug("requestURI: " + requestURI);
	        
	        String name = requestURI.substring(requestURI.lastIndexOf('/'));
	        
	        pageView.setCategory( Utils.populateCategoryForCategoryPage(name.replace("/", "")));
	        
	        
	        if(pageView.getCategory() == null) {
	            Category category = ServiceFactory.getService(CategoryService.class).getCategoryByName(name.replace("/", ""));
	            pageView.setCategory(category);
	        }
	        
	        TabProduct topSelling = SCacheFacade.getTopSelling(0,false);
	        pageView.setTopSellingProducts(topSelling);
	        
	        populateBreadcrumbForPage(pageView.getCategory(), pageView.getBreadcrumb());
	        
	        request.setAttribute(Constants.PAGE_VIEW, pageView);
	        
	       return true;
	}
	
}
