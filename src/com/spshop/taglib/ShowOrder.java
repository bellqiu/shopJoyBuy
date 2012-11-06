package com.spshop.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.spshop.cache.SCacheFacade;
import com.spshop.model.Order;

public class ShowOrder extends TagSupport{
	/**
	 *
	 */
	private static final long serialVersionUID = 100112717716162L;
	private String var = "order";
	private String orderId;
	
	@Override
	public int doStartTag() throws JspException {
		Order order = SCacheFacade.getOrderById(orderId);
		pageContext.setAttribute(var, order);
		return EVAL_BODY_INCLUDE;
	}
	
	public String getVar() {
		return var;
	}
	public void setVar(String var) {
		this.var = var;
	}
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Override
	public void release() {
		super.release();
		 var = "order";
		 orderId = "";
	}
	
	
	
}
