package com.spshop.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.spshop.cache.SCacheFacade;
import com.spshop.model.TabSelling;

public class ShowTabSelling extends TagSupport{
	
	/**
	 *
	 */
	private static final long serialVersionUID = -143961056215358327L;
	private String var = "tabSelling";
	private boolean forceUpdate=false;
	
	@Override
	public int doStartTag() throws JspException {
		
		TabSelling tabSelling = SCacheFacade.getTabSelling(false);
		pageContext.setAttribute(var, tabSelling);
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
	}

}
