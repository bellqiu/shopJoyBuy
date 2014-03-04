package com.spshop.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.output.XMLOutputter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.spshop.cache.SCacheFacade;
import com.spshop.feed.google.rss.AbstractGoogleRSSFeed;
import com.spshop.feed.google.rss.GoogleRSSFeed4US;
import com.spshop.model.Category;
import com.spshop.model.Product;
import com.spshop.utils.Utils;

@Controller
@RequestMapping("/feed/rss")
public class RSSFeedController extends BaseController {
	private static Logger logger = Logger.getLogger(FeedController.class);
    
    @RequestMapping(value="/{countryCode}/{category}/{index}/{size}", produces="application/xml")
    public void categoryMapping(Model model,
                                  HttpServletRequest request,
                                  HttpServletResponse response,
                                  @PathVariable String countryCode,
                                  @PathVariable String category,
                                  @PathVariable String index,
                                  @PathVariable String size) {
        Integer pageSize = 100;
        Integer startIndex = 1;
        String mappedCategory = AbstractGoogleRSSFeed.GoogleCategoryMapper.mapping(category);
        String include2image=request.getParameter("include2image");
        String feedImage = request.getParameter("feedImage");

        if (StringUtils.isNotBlank(index)) {
            startIndex = Integer.valueOf(index);
        }
        if (StringUtils.isNotBlank(size)) {
            pageSize = Integer.valueOf(size);
        }

        List<Product> products = searchProductsByCategory(category, startIndex - 1, startIndex + pageSize - 1);

        Document doc = GoogleRSSFeed4US.buildRSSXMLByProducts(Boolean.valueOf(include2image), Boolean.valueOf(feedImage), products, countryCode, category, mappedCategory, getSiteView().getHost(), getSiteView().getImageHost(), request);
        XMLOutputter xmlOutputter = new XMLOutputter();
        try {
            File file = new File(AbstractGoogleRSSFeed.getProperty("feed.file.location"), generateFileName(category, String.valueOf(startIndex), String.valueOf(startIndex + pageSize)));
            xmlOutputter.output(doc, new FileOutputStream(file));
            //
            response.setContentLength(new Long(file.length()).intValue());
            response.setHeader("Content-Disposition","attachment; filename="+generateFileName(category, String.valueOf(startIndex), String.valueOf(startIndex + pageSize)));
            
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
