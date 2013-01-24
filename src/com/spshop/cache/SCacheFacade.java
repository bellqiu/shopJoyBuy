package com.spshop.cache;

import static com.spshop.utils.Constants.CATEGORY_CACHE;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import com.spshop.model.Category;
import com.spshop.model.Country;
import com.spshop.model.HTML;
import com.spshop.model.Image;
import com.spshop.model.Order;
import com.spshop.model.Product;
import com.spshop.model.Site;
import com.spshop.model.TabProduct;
import com.spshop.model.TabSelling;
import com.spshop.service.factory.ServiceFactory;
import com.spshop.service.intf.CategoryService;
import com.spshop.service.intf.CountryService;
import com.spshop.service.intf.HTMLService;
import com.spshop.service.intf.OrderService;
import com.spshop.service.intf.ProductService;
import com.spshop.service.intf.SiteService;
import com.spshop.service.intf.TabProductService;
import com.spshop.service.intf.TabSellingService;
import com.spshop.utils.Constants;
@SuppressWarnings("unchecked")
public class SCacheFacade{
	private static SCacheManager cacheManager;
	public static final String GLOBAL_CACHE = "global";
	public static final String PRODUCT_CACHE = "product";
	public static final String CART_CACHE = "cart";
	public static final String TAB_PRODUCT_NAME_CACHE = "tabProductNames";
	public static final String CATRGORY_PRODUCT_NAME_CACHE = "categoryProductNames";
	public static final String COUNTRY_CACHE_KEY = "COUNTRY_CACHE_KEY";
	public static final String COUNTRY_CACHE = "countryCache";
	public static final String ORDER_CACHE = "orderCache";
	public static final String COLOR_CACHE = "colorCache";
	 public static final String TAGS_PRODUCT_CACHE  = "tagsProduct";
	
	static{
		cacheManager = new SCacheManager(SCacheFacade.class.getResourceAsStream("/ehcache.xml"));
	}
	
	public static Cache getGlobalCache(){
		return cacheManager.getCache(GLOBAL_CACHE);
	}
	
	public static SCache getContryCache(){
		return cacheManager.getSCache(COUNTRY_CACHE);
	}
	
	public static SCache getProductCache(){
		return cacheManager.getSCache(PRODUCT_CACHE);
	}
	
	public static SCache getCategoryProductNameCache(){
		return cacheManager.getSCache(CATRGORY_PRODUCT_NAME_CACHE);
	}
	
	public static SCache getTabProductNameCache(){
		return cacheManager.getSCache(TAB_PRODUCT_NAME_CACHE);
	}
	
	public static SCache getOrderCache(){
		return cacheManager.getSCache(ORDER_CACHE);
	}
	
	public static Cache getCartCache() {
		return cacheManager.getCache(CART_CACHE);
	}
	
	public static Cache getColorCache(){
		return cacheManager.getCache(COLOR_CACHE);
	}
	
	public static SCache getTagsProductCache(){
		return cacheManager.getSCache(TAGS_PRODUCT_CACHE);
	}

	public static Order getOrder(String userEmail) {
		if (null != getCartCache() && null != getCartCache().get(userEmail)) {
			Order order = (Order) getCartCache().get(userEmail).getValue();
			return order;
		}
		return null;
	}
	
	public static void addOrder(String email, Order order) {
		if (null != getCartCache()){
			getCartCache().put(new Element(email, order));
		}
		
	}
	
	public static void removeOrder(String email) {
		if (null != getCartCache()){
			getCartCache().remove(email);
		}
		
	}
	
	public static List<Category> getTopCategories(){
		CategoryService cs = ServiceFactory.getService(CategoryService.class);
		List<Category> categories = null;
		if(null!=getGlobalCache().get(CATEGORY_CACHE)){
			categories = (List<Category>) getGlobalCache().get(CATEGORY_CACHE).getValue();
		}else{
			categories = cs.getTopCategories();
			getGlobalCache().put(new Element(CATEGORY_CACHE, categories));
		}
		return categories;
	}
	
	public static Site getSite(){
		return getSite(false);
	}
	
	public static Site getSite(boolean faceUpdate){
		SiteService ss = ServiceFactory.getService(SiteService.class);
		Site site = null;
		
		if(!faceUpdate&&null!=getGlobalCache().get(Constants.DEFAULT_SITE_CACHE)){
			site = (Site) getGlobalCache().get(Constants.DEFAULT_SITE_CACHE).getValue();
		}else{
			site = ss.getSiteById(Constants.DEFAULT_SITE_ID);
			getGlobalCache().put(new Element(Constants.DEFAULT_SITE_CACHE, site));
		}
		
		return site;
	}
	
	public static TabProduct getTopSelling(long id ,boolean faceUpdate){
		TabProductService ss = ServiceFactory.getService(TabProductService.class);
		TabProduct tabProduct = null;
		
		if(!faceUpdate&&null!=getGlobalCache().get(TabProduct.class.getName()+id)){
			tabProduct = (TabProduct) getGlobalCache().get(TabProduct.class.getName()+id).getValue();
		}else{
			if(id>0){
				tabProduct = ss.getTopSelling(id);
			}else{
				tabProduct = ss.getTopSelling();
			}
			
			if(null!=tabProduct.getProducts()){
				for(Product product : tabProduct.getProducts()){
					getProductCache().put(product.getName(), product);
				}
			}
			
			getGlobalCache().put(new Element(TabProduct.class.getName()+id, tabProduct));
		}
		
		return tabProduct;
	}
	
	public static HTML getHTML(long id,boolean faceUpdate){
		HTMLService ss = ServiceFactory.getService(HTMLService.class);
		HTML html = null;
		
		if(!faceUpdate&&null!=getGlobalCache().get(HTML.class.getName()+id)){
			html = (HTML) getGlobalCache().get(HTML.class.getName()+id).getValue();
		}else{
			html = ss.getHTML(id);
			getGlobalCache().put(new Element(HTML.class.getName()+id, html));
		}
		
		return html;
	}
	
	public static TabSelling getTabSelling(boolean faceUpdate){
		TabSellingService ss = ServiceFactory.getService(TabSellingService.class);
		TabSelling tabSelling = null;
		
		if(!faceUpdate&&null!=getGlobalCache().get(Constants.DEFAULT_TABSELLING_CACHE)){
			tabSelling = (TabSelling) getGlobalCache().get(Constants.DEFAULT_TABSELLING_CACHE).getValue();
		}else{
			tabSelling = ss.getDefaulTabSelling();
			getGlobalCache().put(new Element(Constants.DEFAULT_TABSELLING_CACHE, tabSelling));
		}
		
		return tabSelling;
	}
	
	
	public static List<String> getTabProductNames(long tabProductId){
		List<String> names = (List<String>) getTabProductNameCache().get(""+tabProductId);
		if(null==names){
			names = ServiceFactory.getService(TabProductService.class).getProductNames(tabProductId);
			getTabProductNameCache().put(""+tabProductId, names);
		}

		return names;
	}
	

	public static List<String> getCategoryProductNames(Category category,int start, int end){
		final String key = category.getName()+"_start_"+start+"_end_"+end;
		List<String> names = (List<String>) getCategoryProductNameCache().get(key);
		if(null==names){
			names = ServiceFactory.getService(ProductService.class).queryProdNameByCategory(category,start,end);
			getCategoryProductNameCache().put(key, names);
		}

		return names;
	}
	
	public static Product getProduct(String name){
		Product product = (Product) getProductCache().get(name);
		
		if(null==product){
			product = ServiceFactory.getService(ProductService.class).getProductByName(name);
			getProductCache().put(name, product);
		}
		
		return product;
	}
	
	public static List<Country> getCounties(){
		List<Country> countries = (List<Country>) getContryCache().get(COUNTRY_CACHE_KEY); 
		
		if(null==countries){
			countries = ServiceFactory.getService(CountryService.class).getAllCountries();
			getContryCache().put(COUNTRY_CACHE_KEY,countries);
		}
		
		return countries;
	}
	
	public static Order getOrderById(String id){
		Order order = (Order) getOrderCache().get("ORDER_"+id);
		
		if(null==order){
			order = ServiceFactory.getService(OrderService.class).getOrderById(id);
			getOrderCache().put("ORDER_"+id, order);
		}
		
		return order;
	}
	
	public static List<Order> getOrdersByUserId(int id){
		List<Order> orders = (List<Order>) getOrderCache().get("User_"+id);
		
		if(null==orders){
			orders = ServiceFactory.getService(OrderService.class).getOrdersByUserId(id);
			getOrderCache().put("User_"+id, orders);
		}
		
		return orders;
	}
	
	
	public static List<Image> getColors(){
		List<Image> colors = (List<Image>) getOrderCache().get("Colors");
		
		if(null==colors){
			colors = ServiceFactory.getService(SiteService.class).getAllColors();
			getOrderCache().put("Colors",colors);
		}
		
		return colors;
	}
	
	public static List<Product> getTagsProducts(String tag, int page){
		String key = tag+"@"+page;
		List<Product> products = (List<Product>) getTagsProductCache().get(key);
		
		if(null == products){
			tag = tag.replace('-', ' ');
			products = ServiceFactory.getService(ProductService.class).getProductsByTag(tag, (page-1)*Constants.TAGS_PRODUCT_PER_PAGE,Constants.TAGS_PRODUCT_PER_PAGE); 
			 getTagsProductCache().put(key, products);
		}
		
		return products;
	}
	
}
