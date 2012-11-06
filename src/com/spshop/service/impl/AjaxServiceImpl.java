package com.spshop.service.impl;

import com.spshop.service.factory.ServiceFactory;
import com.spshop.service.intf.UserService;

public class AjaxServiceImpl {

	public Boolean validateUserByEmail(String email) {
		return ServiceFactory.getService(UserService.class).validateUserByEmail(email);
	}
}
