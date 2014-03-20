package com.spshop.admin.client.businessui.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.spshop.admin.shared.LoginInfo;
import com.spshop.exception.ServiceValidateException;
import com.spshop.model.Category;
import com.spshop.model.Component;
import com.spshop.model.Country;
import com.spshop.model.HTML;
import com.spshop.model.Image;
import com.spshop.model.Message;
import com.spshop.model.Order;
import com.spshop.model.Product;
import com.spshop.model.Site;
import com.spshop.model.TabProduct;
import com.spshop.model.TabSelling;
import com.spshop.model.query.QueryCriteria;
import com.spshop.model.query.QueryResult;

public interface AdminServiceAsync {

	void query(QueryCriteria criteria, AsyncCallback<QueryResult<Component>> callback)throws ServiceValidateException;

	void getLoginInfo(AsyncCallback<LoginInfo> callback)throws ServiceValidateException;

	void getAllCategory(AsyncCallback<List<Category>> callback)throws ServiceValidateException;

	void saveCategory(Category category, AsyncCallback<Category> callback)throws ServiceValidateException;

	void getImageById(long id, AsyncCallback<Image> callback)throws ServiceValidateException;

	void saveProduct(Product product, AsyncCallback<Product> callback) throws ServiceValidateException;

	void getAllCategory(boolean includeDisable,AsyncCallback<List<Category>> callback) throws ServiceValidateException;

	void saveSite(Site site, AsyncCallback<Site> callback)throws ServiceValidateException;

	void getTopSelling(AsyncCallback<TabProduct> callback);

	void saveTopSelling(TabProduct product, AsyncCallback<TabProduct> callback);

	void getDefaulTabSelling(AsyncCallback<TabSelling> callback);

	void saveTabSelling(TabSelling selling, AsyncCallback<TabSelling> callback);

	void getTopSelling(long id, AsyncCallback<TabProduct> callback);

	void saveHTML(HTML html, AsyncCallback<HTML> callback);

	void getHTML(long id, AsyncCallback<HTML> callback);

	void getHTMLs(String ids, AsyncCallback<List<HTML>> callback);

	void saveCountry(Country country, AsyncCallback<Country> callback);

	void getAllCountries(AsyncCallback<List<Country>> callback);

	void getCountryById(long id, AsyncCallback<Country> callback);
	
	void updateOrderStatus(Order order, String status, AsyncCallback<Order> callback);
	
	void queryByHQL(String hql, List<Object> params, String className, int start, int max, AsyncCallback<QueryResult<Component>> callback);
	
	void replyMessage(Message parent, Message message, AsyncCallback<Message> callback);
	
	void queryOrdersByUserId(long userId, AsyncCallback<List<Order>> callback);
}
