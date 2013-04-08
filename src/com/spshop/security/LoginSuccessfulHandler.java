/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.spshop.security;

import static com.spshop.utils.Constants.CURRENCY;
import static com.spshop.utils.Constants.SHOPPINGCART;
import static com.spshop.utils.Constants.USER_INFO;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.spshop.model.User;
import com.spshop.model.cart.ShoppingCart;
import com.spshop.service.factory.ServiceFactory;
import com.spshop.service.intf.UserService;
import com.spshop.utils.Constants;
import com.spshop.utils.Utils;
import com.spshop.web.interceptor.BootstrapDataFilterInterceptor;
import com.spshop.web.view.SiteView;
import com.spshop.web.view.UserView;

/**
 * 
 * @author <link href="wan-shan.zhu@hp.com">Spark Zhu</link>
 * @version 1.0
 */
public class LoginSuccessfulHandler extends SavedRequestAwareAuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		UserDetails detail = (UserDetails) authentication.getPrincipal();
		
		User user = ServiceFactory.getService(UserService.class).queryUserByEmail(detail.getUsername());
				
		request.getSession().setAttribute(USER_INFO, user);
		
		super.onAuthenticationSuccess(request,response,authentication);
		
	}

}
