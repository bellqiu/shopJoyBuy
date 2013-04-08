/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.spshop.web.interceptor;

/**
 * 
 * @author <link href="wan-shan.zhu@hp.com">Spark Zhu</link>
 * @version 1.0
 */
import java.util.List;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.spshop.model.Category;

public abstract class AbstractCategoryDataInterceptor extends  HandlerInterceptorAdapter{

	public AbstractCategoryDataInterceptor() {
		super();
	}

	protected void populateBreadcrumbForPage(Category category, List<Category> breadcrumb) {
	    while (category != null && category.getParent() != null) {
	        populateBreadcrumbForPage(category.getParent(), breadcrumb);
	        break;
	    }
	    breadcrumb.add(category);
	}

}