package com.spshop.taglib;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.spshop.cache.SCacheFacade;
import com.spshop.model.Country;

public class ShowCountries extends TagSupport{
	/**
	 *
	 */
	private static final long serialVersionUID = 100112717716162L;
	private String var = "countries";
	
	@Override
	public int doStartTag() throws JspException {
		List<Country> countries = SCacheFacade.getCounties();
		pageContext.setAttribute(var, countries);
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
		 var = "countries";
	}

	
}
