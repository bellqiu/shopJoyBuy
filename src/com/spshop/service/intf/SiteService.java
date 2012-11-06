package com.spshop.service.intf;

import java.util.List;

import com.spshop.dao.intf.SiteDAO;
import com.spshop.model.Component;
import com.spshop.model.Image;
import com.spshop.model.Site;
import com.spshop.model.query.QueryCriteria;
import com.spshop.model.query.QueryResult;

public interface SiteService extends BaseService<Site,SiteDAO, Long>{

	Site getSiteById(long defaultSiteId);
	QueryResult<Component> query(QueryCriteria criteria);
	QueryResult<Component> queryByHQL(String hql, List<Object> params, String className);
	List<Image> getAllColors();
	Image getColorImgById(long id);
}
