package com.spshop.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.spshop.cache.SCacheFacade;
import com.spshop.model.HTML;

public class ShowHTML extends TagSupport{
	
	/**
	 *
	 */
	private static final long serialVersionUID = -2585682872396456649L;
	private String var = "topSelling";
	private boolean forceUpdate=false;
	private int htmlId;
	
	@Override
	public int doStartTag() throws JspException {
		HTML html = SCacheFacade.getHTML(htmlId,false);
		pageContext.setAttribute(var, html);
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
		 var = "topSelling";
		 forceUpdate=false;
		 htmlId = 0;
	}

	public int getHtmlId() {
		return htmlId;
	}

	public void setHtmlId(int htmlId) {
		this.htmlId = htmlId;
	}
	
}
