package com.spshop.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.spshop.dao.intf.SiteDAO;
import com.spshop.model.Component;
import com.spshop.model.Image;
import com.spshop.model.Site;
import com.spshop.model.query.QueryCriteria;
import com.spshop.model.query.QueryResult;
import com.spshop.service.AbstractService;
import com.spshop.service.intf.SiteService;

public class SiteServiceImpl extends AbstractService<Site,SiteDAO, Long> implements SiteService{

	@Override
	public Site getSiteById(long defaultSiteId) {
		Site site = fetchById(defaultSiteId);
		return site.clone();
	}

	@Override
	public QueryResult<Component> query(QueryCriteria criteria) {
		QueryResult<Component> qs = getDao().queryByHQL(criteria);
		return qs.clone();
	}

    @Override
    public QueryResult<Component> queryByHQL(String hql, List<Object> params, String className) {
        QueryResult<Component> qs = getDao().queryByHQL(hql, params, className);
        return qs.clone();
    }
    
    @Override
    public QueryResult<Component> queryByHQL(String hql, List<Object> params, String className, int start, int max) {
        QueryResult<Component> qs = getDao().queryByHQL(hql, params, className, start, max);
        return qs.clone();
    }

	@Override
	public List<Image> getAllColors() {
		List<Image> rs = new ArrayList<Image>();
		
		 List<Object> imgs= getDao().getAllColors();
		 
		 for (Object object : imgs) {
			 rs.add(((Image)object).clone());
		}
		
		return rs;
	}

	@Override
	public Image getColorImgById(long id) {
		
		Image img = getDao().getColorImgById(id);
		
		if(null != img){
			img = img.clone();
		}
		
		return img;
	}
	
}
