package com.spshop.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.spshop.cache.SCacheFacade;
import com.spshop.model.Product;

public class ShowProduct extends TagSupport{
	
	/**
	 *
	 */
	private static final long serialVersionUID = -2585682872396456649L;
	private String var = "product";
	private boolean forceUpdate=false;
	private String productName;
	
	@Override
	public int doStartTag() throws JspException {
		Product product = SCacheFacade.getProduct(productName);
		pageContext.setAttribute(var, product);
		return EVAL_BODY_INCLUDE;
	}
	
	public String getVar() {
		return var;
	}
	public void setVar(String var) {
		this.var = var;
	}
	public boolean isForceUpdate() {
		return forceUpdate;
	}
	public void setForceUpdate(boolean forceUpdate) {
		this.forceUpdate = forceUpdate;
	}
	
	@Override
	public void release() {
		super.release();
		 var = "product";
		 forceUpdate=false;
		 productName = null;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductName() {
		return productName;
	}
}
