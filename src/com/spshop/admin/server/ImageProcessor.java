package com.spshop.admin.server;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.spshop.model.Image;
import com.spshop.model.Site;
import com.spshop.model.enums.ImageSizeType;
import com.spshop.model.enums.ImageType;
import com.spshop.service.factory.ServiceFactory;
import com.spshop.service.intf.ImageService;

public class ImageProcessor extends RemoteHttp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 477413844967078323L;

	@SuppressWarnings("rawtypes")
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		Site site = getLoginInfo(req).getSite();
		List items = null;
		String name=null;
		ImageSizeType sizeType=null;
		long id=0;
		String fileName=System.nanoTime()+".jpg";
		ImageType type = ImageType.INTERNAL;

		try {
			items = upload.parseRequest(req);
		} catch (FileUploadException ex) {
			throw new ServletException(ex);
		}

		Iterator iter = items.iterator();
		File orignalFile = null;
		while (iter.hasNext()) {
			FileItem item = (FileItem) iter.next();
			if (item.isFormField()) {
				if (item.getFieldName().equals("imageName")) {
					name = item.getString();
				} else if (item.getFieldName().equals("id")) {
					id = null == item.getString() ? 0 : Long.valueOf(item
							.getString());
				}else if (item.getFieldName().equals("imageType")) {
					sizeType = ImageSizeType.valueOf(item.getString());
				}
			} else {
				orignalFile = new File(getServletContext().getRealPath(site.getImagePath()),
						fileName);
				try {
					item.write(orignalFile);
				} catch (Exception e) {
					throw new IOException(e);
				}
			}
		}
		ImageService imageService = ServiceFactory.getService(ImageService.class);
		Image image = new Image();
		image.setSite(site);
		boolean update = id ==0?false:true;
		if(update){
			image = imageService.fetchById(id);
		}else{
			image.setCreateDate(new Date());
		}
		image.setUpdateDate(new Date());
		image.setAltTitle(name);
		image.setName(name);
		image.setSizeType(sizeType);
		image.setType(type);
		image.setNoChangeUrl(site.getImagePath()+"/"+fileName);
		
		Image img = imageService.saveImage(image, orignalFile.getAbsolutePath(), getLoginInfo(req));
		resp.getWriter().print(img.getId());
	}

}
