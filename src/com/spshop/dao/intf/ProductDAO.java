package com.spshop.dao.intf;

import java.util.List;
import java.util.Map;

import com.spshop.model.Category;
import com.spshop.model.Product;

public interface ProductDAO extends BaseDAO<Product, Long>{
	public List<String> queryProdNameByCategory(Category category, int start,
			int end);
	Product getProductByName(String name);
	
	Map<String, String> search(String keyword, int start, int end);
}
