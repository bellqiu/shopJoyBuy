package com.spshop.taglib;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.spshop.model.Category;

public class AncestorCategory extends TagSupport {
    
    private List<Category> categories = new ArrayList<Category>();
    private String ancester = "ancester";

    public int doStartTag() throws JspException {
        if (this.categories.size() != 0) {
            Category tempCat = this.categories.get(0);
            while (tempCat.getParent() != null) {
                tempCat = tempCat.getParent();
            }
            pageContext.setAttribute(this.ancester, tempCat.getName());
        }
        return EVAL_BODY_INCLUDE;
    }

    public void release() {
        super.release();
        this.categories = null;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void setAncester(String ancester) {
        this.ancester = ancester;
    }

    public String getAncester() {
        return ancester;
    }
}
