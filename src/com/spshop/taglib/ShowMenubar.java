package com.spshop.taglib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.spshop.cache.SCacheFacade;
import com.spshop.model.Category;

public class ShowMenubar extends TagSupport {

    /**
     *
     */
    private static final long serialVersionUID = -1801145285751623094L;
    private String items = "items";
    private String specialOffers = "specialOffers";
    private boolean forceUpdate = false;
    

    @Override
    public int doStartTag() throws JspException {
        List<Category> categories = SCacheFacade.getTopCategories();
        Map<Object, Object> specialOfferMap = new HashMap<Object, Object>();
        
        for (Category category : categories) {
            List<Category> specialOffers = new ArrayList<Category>();
            specialOfferMap.put(category.getName(), findSpecialOffers(category.getSubCategories(), specialOffers));
        }
        
        pageContext.setAttribute(this.items, categories);
        pageContext.setAttribute(this.specialOffers, specialOfferMap);
        
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public void release() {
        super.release();
        this.items = null;
        this.specialOffers = null;
        this.forceUpdate = false;
    }
    
    private List<Category> findSpecialOffers(List<Category> categories, List<Category> specialOffers) {
        
        for (Category category : categories) {
            if (category.isSpecialOffer()) {
                specialOffers.add(category);
            } else {
                if (category.getSubCategories().size() != 0) {
                    findSpecialOffers(category.getSubCategories(), specialOffers);
                }
            }
        }
        return specialOffers;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getSpecialOffers() {
        return specialOffers;
    }

    public void setSpecialOffers(String specialOffers) {
        this.specialOffers = specialOffers;
    }

    public boolean isForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }
    

}
