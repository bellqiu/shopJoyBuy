package com.spshop.service.intf;

import com.spshop.admin.shared.LoginInfo;
import com.spshop.dao.intf.ImageDAO;

import com.spshop.model.Image;

public interface ImageService extends BaseService<Image,ImageDAO, Long>{
	Image saveImage(Image image,String imagePath,LoginInfo info);

	Image getImageById(long id);
	
	Image saveImage(Image image);
}
