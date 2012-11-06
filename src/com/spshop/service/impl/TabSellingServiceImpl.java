package com.spshop.service.impl;

import java.util.List;

import com.spshop.dao.intf.TabSellingDAO;
import com.spshop.model.TabSelling;
import com.spshop.service.AbstractService;
import com.spshop.service.intf.TabSellingService;
@SuppressWarnings("unchecked")
public class TabSellingServiceImpl extends AbstractService<TabSelling, TabSellingDAO, Long> implements TabSellingService{
	
	private static final String DEFAULF_TAB_SELLING = "DEFAULF_TAB_SELLING";
	
	@Override
	public TabSelling getDefaulTabSelling() {
		TabSelling tabSelling = null;
		
		List<TabSelling> tabSellings = getDao().queryByHQL("From TabSelling where name = '"+DEFAULF_TAB_SELLING+"'", 0, 2);
		
		if(null==tabSellings||tabSellings.size()<1){
			tabSelling = new TabSelling();
			tabSelling.setName(DEFAULF_TAB_SELLING);
		}else{
			tabSelling = tabSellings.get(0);
		}
		
		return tabSelling.clone();
	}

	@Override
	public TabSelling saveTabSelling(TabSelling selling) {
//		for (TabProduct t:selling.getTabs()) {
//			t = ServiceFactory.getService(TabProductService.class).save(t);
//		}
		selling = merge(selling);
		return selling.clone();
	}

}
