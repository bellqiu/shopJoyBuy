package com.spshop.service.intf;

import com.spshop.dao.intf.TabSellingDAO;
import com.spshop.model.TabSelling;

public interface TabSellingService extends BaseService<TabSelling, TabSellingDAO, Long>{
	public TabSelling getDefaulTabSelling();
	public TabSelling saveTabSelling(TabSelling selling);
}
