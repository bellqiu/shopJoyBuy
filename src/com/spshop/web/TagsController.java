package com.spshop.web;

import static com.spshop.utils.Constants.*;

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
	
	
	@RequestMapping("/index/{indexName}")
	public String tagIndex(Model model, @PathVariable("indexName")String index){
		return "redirect:/tags/index/"+index+"/1";
	}
	
	@RequestMapping("/index/{indexName}/{page}")
	public String tagIndex(Model model, @PathVariable("indexName")String index, @PathVariable("page")int page) {
		String keyword = index.replace("-", " ");
		
		List<String> kws = ServiceFactory.getService(TagsService.class).getTags(keyword, page, KEYWORD_PER_PAGE);
		if(null != kws){
			model.addAttribute(CURRENT_KEYWORD, keyword);
			
			model.addAttribute(CURRENT_INDEX_KEY_LIST, kws);
			
			model.addAttribute(CURRENT_INDEX_KEY_PAGE, page);
			
			model.addAttribute(CURRENT_INDEX_KEY_PAGE_COUNT, ServiceFactory.getService(TagsService.class).getKeyworkCount(keyword));
		}
		
		return "/tags/tagIndex";
	}
	
	@RequestMapping("/index")
	public String tagIndex(Model model) {
		model.addAttribute(TAG_INDEXS, ServiceFactory.getService(TagsService.class).getTagIndexs());
		return "/tags/tagIndexAll";
	}
	

	@RequestMapping("/k/{keyword}/{page}")
	public String tagKey(Model model, @PathVariable("keyword")String key,  @PathVariable("page")int page){
		
		 TabProduct topSelling = SCacheFacade.getTopSelling(0,false);
		 
		 List<Product> products = SCacheFacade.getTagsProducts(key.replace('-', ' '), page);
		 
		 if(null != products){
			 model.addAttribute(CURRENT_INDEX_KEY_PAGE, page);
			 
			 model.addAttribute(TOP_SELLING_TAB_PRODUCT, topSelling);
			 
			 String kw = key.replace('-', ' ');
			 
			 model.addAttribute(CURRENT_KEYWORD_2, kw);
			 
			 model.addAttribute(TAGED_PRODUCTS, products);
			 
			 int count = ServiceFactory.getService(ProductService.class).getProductCountByTag(kw);
			 
			 List<Float> imageSize = Utils.figureOutProductsSize(products);
			 
			 model.addAttribute(IMAGE_SIZE_INFO_KEY , imageSize);
			 
			 model.addAttribute(CURRENT_INDEX_KEY_PAGE_COUNT, count%TAGS_PRODUCT_PER_PAGE==0?count/TAGS_PRODUCT_PER_PAGE:count/TAGS_PRODUCT_PER_PAGE+1);
		 }
		 
		 return "/tags/tagKeyword";
	}
	
	@RequestMapping("/k/{keyword}")
	public String tagKey(Model model, @PathVariable("keyword")String key) {
		
		return "redirect:/tags/k/"+key+"/1";
		
	}
}
