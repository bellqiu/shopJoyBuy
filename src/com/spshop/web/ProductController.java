package com.spshop.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.spshop.cache.SCacheFacade;
import com.spshop.model.Category;
import com.spshop.model.Product;
import com.spshop.utils.Constants;
import com.spshop.web.view.PageView;

@Controller
@RequestMapping("/p")
public class ProductController extends BaseController {
    private final static Properties popupConfig = new Properties();
    private static Logger logger = Logger.getLogger(ProductController.class);
    static {
        try {
            popupConfig.load(FeedController.class.getResourceAsStream("/customizePopup.properties"));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public final static String PRODUCT_UI = "product";
    public final static String PRODUCT_DETAILS = "productDetail";
    public final static String CORRELATION_PRODUCTS = "correlations";
    public final static String ANCESTER_CATEGORY = "ancester";
    public final static String RELATED_PRODUCTS = "relatedProducts";

    @RequestMapping(value = "/{productName}")
    public String renderProduct(Model model, HttpServletRequest request, @PathVariable String productName) {

        Product product = SCacheFacade.getProduct(productName);
        getPageView().addPageProperty(PRODUCT_DETAILS, product);
        prepareCorrelationProducts(model, product.getCategories().get(0));
        figureOutAncesterCategory(model, product.getCategories());
        prepareRelatedProducts(model, product.getTabProductKey());
        prepareCustomizePopup(model, product.getCategories());
        return PRODUCT_UI;
    }

    private void prepareCustomizePopup(Model model, List<Category> categories) {
        String[] categoryArr = null;
        for (Object key : popupConfig.keySet()) {
            categoryArr = popupConfig.getProperty(String.valueOf(key)).split(",");
            for (Category category : categories) {
                if (Arrays.asList(categoryArr).contains(category.getName())) {
                    model.addAttribute("popupType", key);
                    break;
                }
            }
        }

    }

    private void prepareCorrelationProducts(Model model, Category category) {
        List<String> names = SCacheFacade.getCategoryProductNames(category, 0, 9);
        List<Product> correlations = new ArrayList<Product>();
        Product p = null;
        for (String name : names) {
            p = SCacheFacade.getProduct(name);
            correlations.add(p);
        }
        model.addAttribute(CORRELATION_PRODUCTS, correlations);
    }

    private void figureOutAncesterCategory(Model model, List<Category> categories) {
        if (categories.size() != 0) {
            Category tempCat = categories.get(0);
            while (tempCat.getParent() != null) {
                tempCat = tempCat.getParent();
            }
            model.addAttribute(ANCESTER_CATEGORY, tempCat.getName());
        }
    }

    private void prepareRelatedProducts(Model model, long tabId) {
        List<String> names = SCacheFacade.getTabProductNames(tabId);
        List<Product> products = new ArrayList<Product>();
        for (String name : names) {
            products.add(SCacheFacade.getProduct(name));
        }
        model.addAttribute(RELATED_PRODUCTS, products);
    }

    public PageView getPageView() {
        return (PageView)((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getAttribute(Constants.PAGE_VIEW);
    }

}
