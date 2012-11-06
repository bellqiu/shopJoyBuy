package com.spshop.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.spshop.cache.SCacheFacade;
import com.spshop.model.Site;

public class ShowSite extends TagSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = -226429618949310355L;
	
	private String var="site";
	private boolean forceUpdate=false;

	public int doStartTag() throws JspException {
		Site site = SCacheFacade.getSite(forceUpdate);
		
		pageContext.setAttribute(var, site);
		
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
	
	
	public void release() {
		super.release();
		var="site";
		forceUpdate=false;
	}
}
