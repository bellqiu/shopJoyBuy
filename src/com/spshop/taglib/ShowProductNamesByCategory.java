package com.spshop.taglib;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.spshop.cache.SCacheFacade;
import com.spshop.model.Category;

public class ShowProductNamesByCategory extends TagSupport{
	
	/**
	 *
	 */
	private static final long serialVersionUID = -2585682872396456649L;
	private String var = "names";
	private boolean forceUpdate=false;
	private Category category;
	
	@Override
	public int doStartTag() throws JspException {
		List<String> names = SCacheFacade.getCategoryProductNames(category,0,9);
		pageContext.setAttribute(var, names);
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
		 var = "names";
		 forceUpdate=false;
		 category = null;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}

	public Category getCategory() {
		return category;
	}
}
