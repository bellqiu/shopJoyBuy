package com.spshop.web.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.spshop.model.Category;
import com.spshop.model.Site;
import com.spshop.model.TabProduct;

public class PageView extends BaseFrontendView {
    /**
     * 
     */
    private static final long serialVersionUID = 2133777280292635270L;
    
    /**
     * Top selling products
     */
    private TabProduct topSellingProducts;
    
    /**
     *  Site Informations
     */
    private Site site;
    
    /**
     *  Path nodes
     */
    private List<Category> pathNodes = new ArrayList<Category>();
    
    /**
     *  Special offer categories
     */
    private Map<Object, Object> specialOffer = new HashMap<Object, Object>();
    
    /**
     *  Other page properties
     */
    private Map<String, Object> pageProperties = new HashMap<String, Object>();
    
    private List<Category> breadcrumb = new ArrayList<Category>();
    
    public void setSite(Site site) {
        this.site = site;
    }

    public Site getSite() {
        return site;
    }

    public void setPageProperties(Map<String, Object> pageProperties) {
        this.pageProperties = pageProperties;
    }

    public void setPathNodes(List<Category> pathNodes) {
        this.pathNodes = pathNodes;
    }

    public List<Category> getPathNodes() {
        return pathNodes;
    }

    public void setSpecialOffer(Map<Object, Object> specialOffer) {
        this.specialOffer = specialOffer;
    }

    public Map<Object, Object> getSpecialOffer() {
        return specialOffer;
    }

    public Map<String, Object> getPageProperties() {
        return pageProperties;
    }
    
    public synchronized void addPageProperty (String key, Object object) {
        this.pageProperties.put(key, object);
    }

    public void setTopSellingProducts(TabProduct topSellingProducts) {
        this.topSellingProducts = topSellingProducts;
    }

    public TabProduct getTopSellingProducts() {
        return topSellingProducts;
    }

    public void setBreadcrumb(List<Category> breadcrumb) {
        this.breadcrumb = breadcrumb;
    }

    public List<Category> getBreadcrumb() {
        return breadcrumb;
    }
}
