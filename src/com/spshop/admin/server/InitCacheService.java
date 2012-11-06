package com.spshop.admin.server;

import static com.spshop.utils.Constants.CATEGORY_CACHE;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.Element;

import com.spshop.cache.SCacheFacade;
import com.spshop.service.factory.ServiceFactory;
import com.spshop.service.intf.CategoryService;
import com.spshop.service.intf.ProductService;

public class InitCacheService extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7424034770029029671L;
	
	public void init() throws ServletException {
		SCacheFacade.getTopCategories();
		SCacheFacade.getSite();
		SCacheFacade.getTabSelling(true);
		System.out.println("init product Cache............");
		ServiceFactory.getService(ProductService.class).loadAllProduct();
		System.out.println("end product Cache............");
		/*TimerTask timerTask = new TimerTask() {
			
			@Override
			public void run() {
				System.out.println("refresh..");
				ServiceFactory.getService(ProductService.class).loadAllProduct();
			}
		};
		
		Timer timer = new Timer("reFreshProductCache", true);
		timer.schedule(timerTask, 500000, 1200000);*/
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		CategoryService cs = ServiceFactory.getService(CategoryService.class);
		SCacheFacade.getGlobalCache().put(new Element(CATEGORY_CACHE, cs.getTopCategories()));
		SCacheFacade.getSite(true);
		SCacheFacade.getTabSelling(true);
	}
}
