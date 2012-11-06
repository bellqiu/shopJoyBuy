package com.spshop.admin.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spshop.admin.shared.ExceptionType;
import com.spshop.admin.shared.LoginInfo;
import com.spshop.utils.Constants;

public abstract class RemoteHttp extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1376230598507318187L;

	public LoginInfo getLoginInfo(HttpServletRequest request){
		
		LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute(Constants.ADMIN_LOGIN_INFO);
		if((null==loginInfo)||(null==loginInfo.getUserID())){
			throw new RuntimeException(ExceptionType.NO_SIGN_IN.getValue());
		}
		return loginInfo;
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		getLoginInfo(request);
		super.service(request, response);
	}
	
}
