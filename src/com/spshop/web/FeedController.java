package com.spshop.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spshop.cache.SCacheFacade;
import com.spshop.model.Category;
import com.spshop.model.Product;
import com.spshop.utils.EmailTools;
import com.spshop.utils.Utils;

@Controller
public class FeedController extends BaseController {
    private final static Properties feedConfig = new Properties();
    private static Logger logger = Logger.getLogger(EmailTools.class);
    static {
        try {
            feedConfig.load(FeedController.class.getResourceAsStream("/feedConfig.properties"));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
    
    private static class CategoryMapper{
        static String mapping(String category){
            String[] categoryArr = null;
            for (Object key : feedConfig.keySet()) {
                categoryArr = feedConfig.getProperty(String.valueOf(key)).split(",");
                if (Arrays.asList(categoryArr).contains(category)) {
                    return String.valueOf(key);
                }
            }
            return null;
        }
    }
    
    @RequestMapping(value="/{category}/{index}/{size}", produces="application/xml")
    public void categoryMapping(Model model,
                                  HttpServletRequest request,
                                  HttpServletResponse response,
                                  @PathVariable String category,
                                  @PathVariable String index,
                                  @PathVariable String size) {
        final Logger logger = Logger.getLogger(FeedController.class);
        Integer pageSize = 100;
        Integer startIndex = 1;
        String mappedCategory = CategoryMapper.mapping(category);

        if (StringUtils.isNotBlank(index)) {
            startIndex = Integer.valueOf(index);
        }
        if (StringUtils.isNotBlank(size)) {
            pageSize = Integer.valueOf(size);
        }

        List<Product> products = searchProductsByCategory(category, startIndex - 1, startIndex + pageSize - 1);

        Document doc = buildXMLByProducts(products, mappedCategory);
        XMLOutputter xmlOutputter = new XMLOutputter();
        try {
            File file = new File(feedConfig.getProperty("feed.file.location"), generateFileName(category, String.valueOf(startIndex), String.valueOf(startIndex + pageSize)));
            xmlOutputter.output(doc, new FileOutputStream(file));
            FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
        } catch (FileNotFoundException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        }
    }
    
    private String generateFileName(String category, String start, String end){
        return "products" + "-" + category + "-" + start + "-" + end + ".xml";
    }

    private Document buildXMLByProducts(List<Product> products, String category) {
        Element root = new Element("products");

        for (Product product : products) {
            Element e = new Element("product");
            e.setAttribute("id", String.valueOf(product.getId()));
            e.addContent(new Element("name").setText(product.getTitle()));
            e.addContent(new Element("url").setText(getSiteView().getHost() + "/" + product.getName()));
            e.addContent(new Element("description").setText("Buy " + product.getTitle() + " at wholesale price from HoneyBuy.com, all free shipping! Buy Now!"));
            e.addContent(new Element("price").setText("$" + String.valueOf(product.getActualPrice())));
            e.addContent(new Element("category").setText(category));
            e.addContent(new Element("image").setText(getSiteView().getImageHost()
                                                      + product.getImages().get(0).getLargerUrl()));

            root.addContent(e);
        }
        Document doc = new Document(root);

        return doc;
    }

    private List<Product> searchProductsByCategory(String categoryName, int start, int end) {
        List<Category> categories = SCacheFacade.getTopCategories();
        Category category = Utils.searchCategory(categories, categoryName);

        List<Product> products = new ArrayList<Product>();
        List<String> productNames = SCacheFacade.getCategoryProductNames(category, start, end);

        for (String name : productNames) {
            products.add(SCacheFacade.getProduct(name));
        }
        return products;
    }
}
