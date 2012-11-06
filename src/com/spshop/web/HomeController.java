package com.spshop.web;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spshop.model.Email;
import com.spshop.model.TabProduct;
import com.spshop.service.factory.ServiceFactory;
import com.spshop.service.intf.EmailService;
import com.spshop.utils.Constants;
import com.spshop.utils.Utils;
import com.spshop.web.view.HomeView;

@Controller
public class HomeController extends BaseController {

    private HomeView homeView;
    
    @RequestMapping("/")
    public String renderHome(HttpServletRequest request, HttpServletResponse response, Model model) {
        Map<String, List<Float>> tabImageInfo = new LinkedHashMap<String, List<Float>>();
        List<TabProduct> tabs = getHomeView().getTabSelling().getTabs();
        for (int i = 0; i < tabs.size(); i++) {
            tabImageInfo.put(String.valueOf(i), Utils.figureOutProductsSize(tabs.get(i).getProducts()));
        }
        model.addAttribute(Constants.TAB_PRODUCTS_IMAGE_SIZE_INFO, tabImageInfo);
        return "index";
    }
    
    @RequestMapping("/storeEmail")
    public String storeEmail(HttpServletRequest request, HttpServletResponse response, Model model){
        String email = request.getParameter(Constants.SUBSCRIBE_EMAL);
        
        if (StringUtils.isNotBlank(email)) {
            Email e = new Email();
            e.setName(email);
            e.setEmailAddress(email);
            e.setCreateDate(new Date());
            e = ServiceFactory.getService(EmailService.class).saveEmail(e);
            model.addAttribute("userEmail", e);
        }
        return "subscribeEmail";
    }

    public void setHomeView(HomeView homeView) {
        this.homeView = homeView;
    }

    public HomeView getHomeView() {
        return homeView;
    }
}
