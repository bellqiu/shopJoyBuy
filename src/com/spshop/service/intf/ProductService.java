package com.spshop.service.intf;

import java.util.List;
import java.util.Map;

import com.spshop.dao.intf.ProductDAO;
import com.spshop.model.Category;
import com.spshop.model.Component;
import com.spshop.model.Product;
import com.spshop.model.query.QueryResult;

public interface ProductService extends BaseService<Product,ProductDAO, Long>{
	QueryResult<Component> queryByName(String name);

	Product saveProduct(Product product);
	
	List<Product> queryByCategory(Category category, int start, int end);
	
	Long queryCountByCategory(Category category);
	
	List<String> queryProdNameByCategory(Category category, int start, int end);
	
	Product getProductByName(String name);
	
	void loadAllProduct();
	
	List<Product> loadAllProduct(int count);
	
	Map<String,String> search(String keyword, int start, int end);
	
	List<Product> getProductsByTag(String tag, int start, int end);
	
	int getProductCountByTag(String tag);
	
	List<String> getTags();
	
	void updateViews(int hit, long pid);
}
