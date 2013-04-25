package com.spshop.service.impl;

import com.spshop.dao.intf.ProductOptionItemDAO;

import com.spshop.model.ProductOptionItem;

import com.spshop.service.AbstractService;

import com.spshop.service.intf.ProductOptionItemService;

public class ProductOptionItemServiceImpl extends AbstractService<ProductOptionItem,ProductOptionItemDAO, Long> implements ProductOptionItemService{

	@Override
	public float getItemChangePrice(long id) {
		
		ProductOptionItem item = fetchById(id);
		
		if(null != item){
			return item.getPriceChange();
		}
		
		return 0f;
	}

}
