package com.spshop.web.interceptor;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.spshop.cache.SCacheFacade;
import com.spshop.model.Category;
import com.spshop.model.TabProduct;
import com.spshop.service.factory.ServiceFactory;
import com.spshop.service.intf.CategoryService;
import com.spshop.utils.Constants;
import com.spshop.utils.Utils;
import com.spshop.web.PageController;
import com.spshop.web.view.PageView;

public class PageDataInterceptor extends HandlerInterceptorAdapter{

    
    private Logger logger = Logger.getLogger(ViewDataInterceptor.class);
    protected Map<String, Float> currencies;
    
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        
        PageView pageView = new PageView();
        
        String categoryName = request.getPathInfo().substring(1);
        Utils.populateCategoryForCategoryPage(categoryName, pageView);
        
        if(pageView.getCategory() == null) {
            Category category = ServiceFactory.getService(CategoryService.class).getCategoryByName(categoryName);
            pageView.setCategory(category);
        }
        populateBreadcrumbForPage(pageView.getCategory(), pageView.getBreadcrumb());
        
        TabProduct topSelling = SCacheFacade.getTopSelling(0,false);
        pageView.setTopSellingProducts(topSelling);
        
        if(handler instanceof PageController){
            PageController controller = (PageController) handler;
            //Page View
            controller.setPageView(pageView);
        }
        
        return true;
    }
    
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            if(handler instanceof PageController){
                PageController controller = (PageController) handler;
                modelAndView.addObject(Constants.PAGE_VIEW, controller.getPageView());
            }
        }
    }
    
    void populateBreadcrumbForPage(Category category, List<Category> breadcrumb) {
        while (category.getParent() != null) {
            populateBreadcrumbForPage(category.getParent(), breadcrumb);
            break;
        }
        breadcrumb.add(category);
    }
}
