package com.spshop.admin.client.businessui.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
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
@RemoteServiceRelativePath("admin")
public interface AdminService  extends RemoteService{
	QueryResult<Component> query(QueryCriteria criteria) throws IllegalArgumentException;
	LoginInfo getLoginInfo()throws IllegalArgumentException;
	List<Category> getAllCategory()throws ServiceValidateException;
	List<Category> getAllCategory(boolean includeDisable)throws ServiceValidateException;
	Category saveCategory(Category category)throws ServiceValidateException;
	Image getImageById(long id)throws ServiceValidateException;
	Product saveProduct(Product product)throws ServiceValidateException;
	Site saveSite(Site site)throws ServiceValidateException;
	TabProduct getTopSelling() throws ServiceValidateException;
	TabProduct saveTopSelling(TabProduct product) throws ServiceValidateException;
	TabSelling getDefaulTabSelling() throws ServiceValidateException;
	TabSelling saveTabSelling(TabSelling selling) throws ServiceValidateException;
	TabProduct getTopSelling(long id) throws ServiceValidateException;
	HTML saveHTML(HTML html) throws ServiceValidateException;
	HTML getHTML(long id) throws ServiceValidateException;
	List<HTML> getHTMLs(String ids) throws ServiceValidateException;
	Country saveCountry(Country country) throws ServiceValidateException;
	public List<Country> getAllCountries() throws ServiceValidateException;
	public Country getCountryById(long id) throws ServiceValidateException;
	Order updateOrderStatus(Order order,String status);
	QueryResult<Component> queryByHQL(String hql, List<Object> params, String className) throws IllegalArgumentException;
	Message replyMessage(Message parent, Message message);
}

