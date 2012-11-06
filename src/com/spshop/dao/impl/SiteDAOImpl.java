package com.spshop.dao.impl;

import java.util.List;

import com.spshop.dao.AbstractBaseDAO;

import com.spshop.dao.intf.SiteDAO;

import com.spshop.model.Image;
import com.spshop.model.Site;
@SuppressWarnings("unchecked")
public class SiteDAOImpl extends AbstractBaseDAO<Site, Long>  implements SiteDAO{

	@Override
	public List<Object> getAllColors() {
		String hql = "from Image where strSizeType = 'PRODUCT_COLOR'";
		List<Object> rs = queryByHQL(hql, 0, 1000);
		return rs;
	}

	@Override
	public Image getColorImgById(long id) {
		String hql = "from Image where id = ?";
		List<Object> rs = (List<Object>) queryByHQL(hql,0,999, id);
		if(null != rs && rs.size()>0){
			return (Image) rs.get(0);
		}
		return null;
	}

}
