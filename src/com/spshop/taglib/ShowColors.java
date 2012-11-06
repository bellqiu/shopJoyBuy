package com.spshop.taglib;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.spshop.cache.SCacheFacade;
import com.spshop.model.Image;

public class ShowColors extends TagSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = -226429618949310355L;
	
	private String var="colors";

	public int doStartTag() throws JspException {
		
		List<Image> colors = SCacheFacade.getColors();
		
		pageContext.setAttribute(var, colors);
		
		return EVAL_BODY_INCLUDE;
	}
	
	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	
	public void release() {
		super.release();
		var="colors";
	}
}
