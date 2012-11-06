package com.spshop.dao.intf;

import java.util.List;

import com.spshop.model.Image;
import com.spshop.model.Site;

public interface SiteDAO extends BaseDAO<Site, Long>{
	List<Object> getAllColors();
	Image getColorImgById(long id);
}
