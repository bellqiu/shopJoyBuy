package com.spshop.taglib;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.spshop.cache.SCacheFacade;
import com.spshop.model.Order;

public class ShowUserOrder extends TagSupport{
	/**
	 *
	 */
	private static final long serialVersionUID = 100112717716162L;
	private String var = "orders";
	private int userId;
	
	@Override
	public int doStartTag() throws JspException {
		List<Order> orders = SCacheFacade.getOrdersByUserId(userId);
		pageContext.setAttribute(var, orders);
		return EVAL_BODY_INCLUDE;
	}
	
	public String getVar() {
		return var;
	}
	public void setVar(String var) {
		this.var = var;
	}
	
	@Override
	public void release() {
		super.release();
		 var = "orders";
		 userId = 0;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getUserId() {
		return userId;
	}

	
}
