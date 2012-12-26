package com.spshop.web.interceptor;

import static com.spshop.utils.Constants.CURRENCY;
import static com.spshop.utils.Constants.CURRENT_PRODUCT_ID;
import static com.spshop.utils.Constants.DEFAULT_CURRENCY;
import static com.spshop.utils.Constants.LOGIN_LANDING_PAGE_PARAM;
import static com.spshop.utils.Constants.LOGIN_PAGE;
import static com.spshop.utils.Constants.LOGOUT_ACTION;
import static com.spshop.utils.Constants.REG_PAGE;
import static com.spshop.utils.Constants.SHOPPINGCART;
import static com.spshop.utils.Constants.USER_VIEW;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.spshop.cache.SCacheFacade;
import com.spshop.model.Category;
import com.spshop.model.Country;
import com.spshop.model.Site;
import com.spshop.model.User;
import com.spshop.model.cart.ShoppingCart;
import com.spshop.service.factory.ServiceFactory;
import com.spshop.service.intf.CountryService;
import com.spshop.utils.Utils;
import com.spshop.web.BaseController;
import com.spshop.web.view.SiteView;
import com.spshop.web.view.UserView;

public class ViewDataInterceptor extends HandlerInterceptorAdapter{
	
	private Logger logger = Logger.getLogger(ViewDataInterceptor.class);
	protected Map<String, Float> currencies;
	
	public ViewDataInterceptor() {
		Properties cp = new Properties();
		try {
			currencies = new HashMap<String, Float>();
			cp.load(ViewDataInterceptor.class.getResourceAsStream("/currency.properties"));
			for (Object currencyName : cp.keySet()) {
				try {
					float rate = Float.parseFloat(cp.get(currencyName).toString());
					currencies.put(currencyName.toString().trim(), rate);
				} catch (NumberFormatException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
			
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		SiteView siteView = initSiteView();
		UserView userView = new UserView();
		//TODO retrieve userView
		User user = Utils.retrieveUser(request);
		ShoppingCart shoppingCart = Utils.retrieveShoppingCart(request, user);
		
		userView.setLoginUser(user);
		userView.setCart(shoppingCart);
		userView.setCurrencyRateMap(currencies);
		
		setCurrency(request,userView,siteView.getCurrencies());
		
		setCurrentProductID(request);
		
		String landingPage = request.getParameter(LOGIN_LANDING_PAGE_PARAM);
		if(StringUtils.isBlank(landingPage)){
			String url = request.getRequestURL().toString();
			if(url.endsWith(LOGIN_PAGE)||url.endsWith(REG_PAGE)){
				url = null;
			}
			String queryString = request.getQueryString();
			if(null != queryString && null != url){
				url = url + "?" + queryString;
				
			}
			if(null!=url){
				userView.setRequestPage(URLEncoder.encode(url,"UTF-8"));
			}
		}else{
			userView.setRequestPage(landingPage);
		}
		
		if(handler instanceof BaseController){
			BaseController controller = (BaseController) handler;
			
			//Site View
			controller.setSiteView(siteView);
			//User View
			controller.setUserView(userView);
		}
		
		return true;
	}
	
	
	private void setCurrentProductID(HttpServletRequest request) {
		String cpid = request.getParameter(CURRENT_PRODUCT_ID);
		
		if(StringUtils.isNotBlank(cpid)){
			request.getSession().setAttribute(CURRENT_PRODUCT_ID, cpid);
		}
	}

	private void setCurrency(HttpServletRequest request, UserView userView, Map<String, Float> currencies) {
		
		String cCode = request.getParameter(CURRENCY);
		
		if(null == cCode){
			cCode = (String) request.getSession().getAttribute(CURRENCY);
		}
		 
		if(null == cCode || !currencies.containsKey(cCode)){
			cCode = DEFAULT_CURRENCY;
		}
		
		userView.setCurrencyCode(cCode);
		userView.getCart().getOrder().setCurrency(cCode);
		
		float rate = 1;
		
		if(!DEFAULT_CURRENCY.equals(cCode)){
			rate = currencies.get(cCode);
		}
		
		userView.setCurrencyRate(rate);
		
		request.getSession().setAttribute(CURRENCY, cCode);
		
	}

	
	private SiteView initSiteView() {
		SiteView siteView = new SiteView();
		
		Site site = SCacheFacade.getSite();
		List<Category> categories = SCacheFacade.getTopCategories();
		String host = "http://"+site.getDomain();
		
		siteView.setHost(host);
		siteView.setSite(site);
		siteView.setCurrencies(this.currencies);
		siteView.setCategories(categories);
		siteView.setImageHost(host);
		
		List<Country> countries = ServiceFactory.getService(CountryService.class).getAllCountries();
		
		Map<String, Country> cMap = new HashMap<String,Country>();
		
		for (Iterator iterator = countries.iterator(); iterator.hasNext();) {
			Country country = (Country) iterator.next();
			cMap.put(country.getId()+"", country);
		}
		
		siteView.setCountries(countries);
		siteView.setCountryMap(cMap);
		
		return siteView;
	}

	

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if(null!=modelAndView && !Boolean.TRUE.toString().equals(modelAndView.getModel().get(LOGOUT_ACTION))){
			UserView userView = (UserView) modelAndView.getModel().get(USER_VIEW);
			if(null != userView){
				request.getSession().setAttribute(SHOPPINGCART, userView.getCart());
				request.getSession().setAttribute(CURRENCY, userView.getCurrencyCode());
			}
		}
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}
	
}
