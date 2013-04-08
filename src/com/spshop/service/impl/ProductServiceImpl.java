package com.spshop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.spshop.cache.SCache;
import com.spshop.cache.SCacheFacade;
import com.spshop.dao.intf.ProductDAO;
import com.spshop.exception.ServiceValidateException;
import com.spshop.model.Category;
import com.spshop.model.Component;
import com.spshop.model.Product;
import com.spshop.model.query.QueryCriteria;
import com.spshop.model.query.QueryResult;
import com.spshop.service.AbstractService;
import com.spshop.service.intf.ProductService;
import com.spshop.validator.ProductValidator;

public class ProductServiceImpl extends AbstractService<Product,ProductDAO, Long> implements ProductService{

	
	@Override
	public QueryResult<Component> queryByName(String name) {
		QueryCriteria criteria = new QueryCriteria();
		criteria.setType(Product.class.getName());
		criteria.setStartIndex(0);
		criteria.setMaxResult(20);
		criteria.setKey(name);
		criteria.addProperty("deleted", false);
		criteria.setOrderBy("id");
		criteria.setAsc(true);
		QueryResult<Component> qs = getDao().queryByHQL(criteria);
		return qs.clone();
	}

	@Override
	public Product saveProduct(Product product) {
		String name = product.getName();
		if(null!=name){
			name = name.trim();
			name = name.replaceAll("\\s+", "-");
			product.setName(name);
		}
		String title = product.getTitle();
		
		if(null != title){
			product.setTitle(title.trim());
		}
		
		if(null!=product.getTags()){
			name = name.replaceAll("\\s+", "-");
			product.setTags(product.getTags().replaceAll("\\s+", " "));
		}
		new ProductValidator(product).validate();
		if(product.getId()<1&&!queryByName(product.getName()).getResult().isEmpty()){
			throw new ServiceValidateException(product.getName()+" is already exist!");
		}
		
		getDao().save(product);
		
		return product.clone();
	}

	@Override
	public List<Product> queryByCategory(Category category, int start, int end) {
		
		String hql = "select p from Product as p join p.categories as ps where p.deleted = false and ps.id = " +category.getId() +" order by p.id desc";
		
		@SuppressWarnings("unchecked")
		List<Product> ps = getDao().queryByHQL(hql,start-1,end);
		
		for(Product product : ps){
			product = product.clone();
		}
		
		return ps;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Long queryCountByCategory(Category category) {
	    String hql = "select count(p) from Product as p join p.categories as ps where  p.deleted = false and ps.id = " +category.getId() +" order by p.id desc";
	    
	    List count = (List)getDao().queryByHQL(hql);
	    
	    return (Long)count.get(0);
	}

	@Override
	public List<String> queryProdNameByCategory(Category category, int start,
			int end) {
		
		return getDao().queryProdNameByCategory(category,start,end);
	}

	@Override
	public Product getProductByName(String name) {
		Product product = getDao().getProductByName(name);
		if(null==product){
			return null;
		}
		return product.clone();
	}

	@Override
	public void loadAllProduct() {
		String hql = "from Product where deleted = false";
		List products = getDao().queryByHQL(hql, 0, 1000);
		SCache sCache = SCacheFacade.getProductCache();
		for (Object object : products) {
			Product product = (Product) object;
			product=product.clone();
			sCache.put(product.getName(), product);
		}
	}

	@Override
	public List<Product> loadAllProduct(int count) {
	    String hql = "from Product where deleted = false";
        List productSource = getDao().queryByHQL(hql, 0, count);
        List products = new ArrayList<Product>();
        for (Object object : productSource) {
            Product product = (Product) object;
            product=product.clone();
            products.add(product);
        }
        return products;
	}
	
	@Override
	public Map<String,String> search(String keyword, int start, int end) {
		return getDao().search(keyword, start, end);
	}
	
	
	@Override
	public int getProductCountByTag(String tag) {
		
		String hql = "select count(id) From Product where deleted = false and tags like ? order by createDate desc"; 
		
		List<Object> count= (List<Object>) getDao().queryByHQL(hql,0,9999,"%"+tag+"%");
		
		return Integer.valueOf(count.get(0).toString());
	}
	

	@Override
	public List<Product> getProductsByTag(String tag, int start, int max) {
		
		String hql = "From Product where deleted = false and tags like ? order by createDate desc"; 
		
		List<Product> products= (List<Product>) getDao().queryByHQL(hql, start, max,"%"+tag+"%");
		
		if(null != products){
			for (Product product : products) {
				product = product.clone();
			}
		}
		
		return products;
	}

	@Override
	public List<String> getTags() {
		
		String hql = "select tags from Product where deleted = false";
		
		List<String>  rs =  (List<String>) getDao().queryByHQL(hql);
		
		return rs;
	}
	
	
}
