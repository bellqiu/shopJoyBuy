package com.spshop.dao.intf;

import java.util.List;

import com.spshop.model.TabProduct;

public interface TabProductDAO extends BaseDAO<TabProduct, Long>{
	List<String> getProductNames(long id);
}
