package com.spshop.web;

import static com.spshop.utils.Constants.CURRENT_INDEX_KEY_LIST;
import static com.spshop.utils.Constants.CURRENT_INDEX_KEY_PAGE;
import static com.spshop.utils.Constants.CURRENT_KEYWORD;
import static com.spshop.utils.Constants.CURRENT_KEYWORD_2;
import static com.spshop.utils.Constants.IMAGE_SIZE_INFO_KEY;
import static com.spshop.utils.Constants.TAGED_PRODUCTS;
import static com.spshop.utils.Constants.TAGS_PRODUCT_PER_PAGE;
import static com.spshop.utils.Constants.TAG_INDEXS;
import static com.spshop.utils.Constants.TOP_SELLING_TAB_PRODUCT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spshop.cache.SCacheFacade;
import com.spshop.model.Product;
import com.spshop.model.TabProduct;
import com.spshop.service.factory.ServiceFactory;
import com.spshop.service.intf.ProductService;
import com.spshop.service.intf.TagsService;
import com.spshop.utils.Utils;
@Controller
public class TagsController extends BaseController{
	
	public static int KEYWORD_PER_PAGE = 120;
	private static final int MAX_PAGE_INDEXES_DISPLAY = 5;
    private static final String KEYWORD_COUNT = "keywordsCount";
    private static final String PRODUCTS_COUNT = "productsCount";
    private static String PAGE_INDEX = "pageIndex";
    private static String MAX_PAGE_NUM = "maxPageNum";
    private static String START_INDEX = "startIndex";
    private static String END_INDEX = "endIndex";
    private static String PAGE_NUM = "pageNum";
    private static String FIRST_PAGE_INDEX = "firstPageIdx";
	
	@RequestMapping("/index/{indexName}")
	public String tagIndex(Model model, @PathVariable("indexName")String index){
		return "redirect:/tags/index/"+index+"/1";
	}
	
	@RequestMapping("/index/{indexName}/{page}")
	public String tagIndex(Model model, @PathVariable("indexName")String index, @PathVariable("page")int pageNum) {
		String keyword = index.replace("-", " ");
		
		List<String> kws = ServiceFactory.getService(TagsService.class).getTags(keyword, pageNum, KEYWORD_PER_PAGE);
		if(null != kws){
			model.addAttribute(CURRENT_KEYWORD, keyword);
			model.addAttribute(CURRENT_INDEX_KEY_LIST, kws);
			
			int count = ServiceFactory.getService(TagsService.class).getKeywordCount(keyword);
			List<Integer> pageIndexes = new ArrayList<Integer>();
			int maxPageNum = (int)((count-1)/KEYWORD_PER_PAGE+1);
			if (maxPageNum >= MAX_PAGE_INDEXES_DISPLAY) {
			    int rTempIdx = pageNum, lTempIdx = pageNum;
			    pageIndexes.add(pageNum);
			    while (pageIndexes.size()<MAX_PAGE_INDEXES_DISPLAY) {
                    if (rTempIdx + 1 <= maxPageNum) {
                        rTempIdx++;
                        pageIndexes.add(rTempIdx);
                    }
                    if (lTempIdx - 1 > 0) {
                        lTempIdx--;
                        pageIndexes.add(lTempIdx);
                    }
                }
			    Collections.sort(pageIndexes);
            } else {
                for (int i = 1; i < maxPageNum; i++) {
                    pageIndexes.add(i);
                }
            }
			if (!pageIndexes.contains(maxPageNum)) {
                model.addAttribute(MAX_PAGE_NUM, maxPageNum);
            }
            if (!pageIndexes.contains(1)) {
                model.addAttribute(FIRST_PAGE_INDEX, 1);
            }
            model.addAttribute(PAGE_INDEX, pageIndexes);
            model.addAttribute(KEYWORD_COUNT, count);
            model.addAttribute(START_INDEX, KEYWORD_PER_PAGE * (pageNum - 1) + 1);
            model.addAttribute(END_INDEX, KEYWORD_PER_PAGE * (pageNum - 1) + kws.size());
            model.addAttribute(PAGE_NUM, pageNum);
		}
		
		return "/tags/tagIndex";
	}
	
	@RequestMapping("/index")
	public String tagIndex(Model model) {
		model.addAttribute(TAG_INDEXS, ServiceFactory.getService(TagsService.class).getTagIndexs());
		return "/tags/tagIndexAll";
	}
	

	@RequestMapping("/k/{keyword}/{page}")
	public String tagKey(Model model, @PathVariable("keyword")String key,  @PathVariable("page")int pageNum){
		
		 TabProduct topSelling = SCacheFacade.getTopSelling(0,false);
		 
		 List<Product> products = SCacheFacade.getTagsProducts(key.replace('-', ' '), pageNum);
         String kw = key.replace('-', ' ');
		 
		 if(null != products){
	            model.addAttribute(CURRENT_INDEX_KEY_PAGE, pageNum);
	            model.addAttribute(TOP_SELLING_TAB_PRODUCT, topSelling);
	            model.addAttribute(TAGED_PRODUCTS, products);
	            
	            int count = ServiceFactory.getService(ProductService.class).getProductCountByTag(kw);
	            List<Integer> pageIndexes = new ArrayList<Integer>();
	            int maxPageNum = (int)((count-1)/TAGS_PRODUCT_PER_PAGE+1);
	            if (maxPageNum >= MAX_PAGE_INDEXES_DISPLAY) {
	                int rTempIdx = pageNum, lTempIdx = pageNum;
	                pageIndexes.add(pageNum);
	                while (pageIndexes.size()<MAX_PAGE_INDEXES_DISPLAY) {
	                    if (rTempIdx + 1 <= maxPageNum) {
	                        rTempIdx++;
	                        pageIndexes.add(rTempIdx);
	                    }
	                    if (lTempIdx - 1 > 0) {
	                        lTempIdx--;
	                        pageIndexes.add(lTempIdx);
	                    }
	                }
	                Collections.sort(pageIndexes);
	            } else {
	                for (int i = 1; i < maxPageNum; i++) {
	                    pageIndexes.add(i);
	                }
	            }
	            if (!pageIndexes.contains(maxPageNum)) {
	                model.addAttribute(MAX_PAGE_NUM, maxPageNum);
	            }
	            if (!pageIndexes.contains(1)) {
	                model.addAttribute(FIRST_PAGE_INDEX, 1);
	            }
	            List<Float> imageSize = Utils.figureOutProductsSize(products);
	            model.addAttribute(IMAGE_SIZE_INFO_KEY , imageSize);
	            model.addAttribute(CURRENT_KEYWORD_2, kw);
	            model.addAttribute(PAGE_INDEX, pageIndexes);
	            model.addAttribute(PRODUCTS_COUNT, count);
	            model.addAttribute(START_INDEX, KEYWORD_PER_PAGE * (pageNum - 1) + 1);
	            model.addAttribute(END_INDEX, KEYWORD_PER_PAGE * (pageNum - 1) + products.size());
	            model.addAttribute(PAGE_NUM, pageNum);
	        }
		 
		 return "/tags/tagKeyword";
	}
	
	@RequestMapping("/k/{keyword}")
	public String tagKey(Model model, @PathVariable("keyword")String key) {
		
		return "redirect:/tags/k/"+key+"/1";
		
	}
}
