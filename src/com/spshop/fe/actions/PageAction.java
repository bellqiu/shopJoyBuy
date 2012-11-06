package com.spshop.fe.actions;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.spshop.cache.SCacheFacade;
import com.spshop.fe.formbeans.PageFormBean;
import com.spshop.model.Category;
import com.spshop.model.Product;
import com.spshop.service.factory.ServiceFactory;
import com.spshop.service.intf.CategoryService;
import com.spshop.service.intf.ProductService;
import com.spshop.utils.Constants;

public class PageAction extends BaseAction {
	@Override
	public ActionForward processer(ActionMapping mapping, PageFormBean page, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String[] uris = request.getRequestURI().split(Constants.URL_SEPERATOR);
		List<Category> pathNodes = new ArrayList<Category>();
		
		if (uris.length != 0) {
			if (Constants.CATEGORY_URL.equals(uris[1])) {
			    String current = request.getParameter(Constants.PAGE_NUM);
			    Integer pageSize = 36;
			    Integer pageNum = 1;
			    
			    if (current != null && !"".equals(current)) {
                    pageNum = Integer.valueOf(current);
                }
			    
			    populateCategoryForCategoryPage(uris[2], page);
			    if(page.getCategory() == null) {
			        Category category = ServiceFactory.getService(CategoryService.class).getCategoryByName(uris[2]);
			        page.setCategory(category);
			    }
			    if (page.getCategory().isDisplayMarketOnly()) {
		            return mapping.findForward(Constants.SPECIAL_CATEGORY_VALUE);
                }
			    populatePathNodesForPage(page.getCategory(), pathNodes);
			    if (page.getCategory().getSubCategories().size() == 0) {
			        populateProductsByCategory(page, pageSize * (pageNum - 1) + 1, pageSize, pageNum, request);
                } else {
                    populateContentByCategory(page, request);
                }
				
			}
			
			page.setPathNodes(pathNodes);
		} else {
			populateCategoryForCategoryPage("home", page);
		}
		return mapping.findForward(Constants.SUCCESS_VALUE);
	}
	
    private void populateContentByCategory(PageFormBean page, HttpServletRequest request) {
        List<Category> categories4Search = new ArrayList<Category>(page.getCategory().getSubCategories());
        Map<Category, List<Product>> content = new LinkedHashMap<Category, List<Product>>();
        List<Product> restProducts = new ArrayList<Product>();
        
        for (Category category : categories4Search) {
            content.put(category, searchProductsByCategory(category, 0, 6));
        }
        page.addPageProperty("subCategoryProducts", content);
        if (content.size() < 6) {
            restProducts = searchProductsByCategory(page.getCategory(), 0, (6-content.size()) * 6 - 1);
            page.addPageProperty("restProducts", restProducts);
        }
    }

    private void populateProductsByCategory(PageFormBean page, int startIndex, int pageSize, int pageNum, HttpServletRequest request) {
        List<Product> products = searchProductsByCategory(page.getCategory(), startIndex - 1, startIndex + pageSize - 1);
        page.addPageProperty(Constants.PROD_IN_CATEGORY_PAGE, products);
        
        if(page.getPageProperties().get(Constants.PROD_IN_CATEGORY_PAGE) != null){
            List<Product> tempProds = (ArrayList<Product>) page.getPageProperties().get(Constants.PROD_IN_CATEGORY_PAGE);
            Long count = ServiceFactory.getService(ProductService.class).queryCountByCategory(page.getCategory());
            List<Integer> pageIndexes = new ArrayList<Integer>();
            for (int i = 1; i <= (count-1)/pageSize+1; i++) {
                pageIndexes.add(i);
            }
            
            request.setAttribute(Constants.PAGE_INDEX, pageIndexes);
            request.setAttribute(Constants.PROD_COUNT, count);
            request.setAttribute(Constants.START_INDEX, pageSize * (pageNum - 1) + 1);
            request.setAttribute(Constants.END_INDEX, pageSize * (pageNum - 1) + tempProds.size());
            request.setAttribute(Constants.PAGE_NUM, pageNum);
        }
    }
    
    private List<Product> searchProductsByCategory(Category category, int start, int end){
        List<Product> products = new ArrayList<Product>();
        List<String> productNames = SCacheFacade.getCategoryProductNames(category, start, end);
        for (String name : productNames) {
            products.add(SCacheFacade.getProduct(name));
        }
        return products;
    }

}
