package com.spshop.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.spshop.cache.SCacheFacade;
import com.spshop.model.Category;
import com.spshop.model.TabSelling;
import com.spshop.service.factory.ServiceFactory;
import com.spshop.service.intf.CategoryService;
import com.spshop.utils.Constants;
import com.spshop.utils.Utils;
import com.spshop.web.HomeController;
import com.spshop.web.view.HomeView;

public class HomeDataInterceptor extends HandlerInterceptorAdapter {
    private final static String HOME_CATEGORY_NAME = "home";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HomeView homeView = new HomeView();
        Utils.populateCategoryForCategoryPage(HOME_CATEGORY_NAME, homeView);
        if (homeView.getCategory() == null) {
            Category category = ServiceFactory.getService(CategoryService.class).getCategoryByName(HOME_CATEGORY_NAME);
            homeView.setCategory(category);
        }
        
        TabSelling tabSelling = SCacheFacade.getTabSelling(false);
        homeView.setTabSelling(tabSelling);
        
        if(handler instanceof HomeController){
            HomeController controller = (HomeController) handler;
            //Home View
            controller.setHomeView(homeView);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            if (handler instanceof HomeController) {
                HomeController controller = (HomeController)handler;
                modelAndView.addObject(Constants.HOME_VIEW, controller.getHomeView());
            }
        }
    }
}
