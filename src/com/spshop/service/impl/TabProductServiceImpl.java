package com.spshop.service.impl;

import java.util.List;

import com.spshop.dao.intf.TabProductDAO;
import com.spshop.model.TabProduct;
import com.spshop.service.AbstractService;
import com.spshop.service.intf.TabProductService;

public class TabProductServiceImpl extends AbstractService<TabProduct, TabProductDAO, Long> implements TabProductService{
	
	private static final String DEFAUL_TOP_SELLING = "DEFAUL_TOP_SELLING";
	
	@SuppressWarnings("unchecked")
	@Override
	public TabProduct getTopSelling() {
		
		TabProduct tb = null;
		
		List<TabProduct> tbs = getDao().queryByHQL("From TabProduct where name = '"+DEFAUL_TOP_SELLING+"'", 0, 10);
		
		if(null==tbs || tbs.isEmpty()){
			tb = new TabProduct();
			tb.setName(DEFAUL_TOP_SELLING);
		}else{
			tb = tbs.get(0);
		}
		
		return tb.clone();
	}

	@Override
	public TabProduct saveTopSelling(TabProduct product) {
		product  = save(product);
		return product.clone();
	}

	@Override
	public TabProduct getTopSelling(long id) {
		return getDao().findById(id).clone();
	}

	@Override
	public List<String> getProductNames(long id) {
		return getDao().getProductNames(id);
	}
}
