package com.spshop.dao.impl;

import java.util.List;

import com.spshop.dao.AbstractBaseDAO;
import com.spshop.dao.intf.TabProductDAO;
import com.spshop.model.TabProduct;

public class TabProductDAOImpl extends AbstractBaseDAO<TabProduct, Long> implements TabProductDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getProductNames(long id) {
		return getSession().createQuery("select p.name From TabProduct as tp join tp.products as p where tp.id=?")
							.setFirstResult(0).setLong(0, id).list();
	}

}
