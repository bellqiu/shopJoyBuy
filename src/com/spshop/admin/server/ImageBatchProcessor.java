package com.spshop.admin.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import com.spshop.admin.shared.LoginInfo;
import com.spshop.model.Image;
import com.spshop.model.enums.ImageSizeType;
import com.spshop.service.factory.ServiceFactory;
import com.spshop.service.intf.ImageService;
@SuppressWarnings("rawtypes")
public class ImageBatchProcessor extends RemoteHttp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 477413844967078323L;
	private static final int BUFFER = 512;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		LoginInfo loginInfo = null;
		List items = null;
		String unZipDir = getServletContext().getRealPath("/tempZip");
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		loginInfo = getLoginInfo(req);
		String zipFileName = System.nanoTime() + ".zip";
		String sizeType = ImageSizeType.PRODUCT_NORMAL.getValue();
		try {
			items = upload.parseRequest(req);
		} catch (FileUploadException ex) {
			throw new ServletException(ex);
		}
		FileItem zipFile = null;
		Iterator iter = items.iterator();
		File orignalFile = null;
		while (iter.hasNext()) {
			FileItem item = (FileItem) iter.next();
			if (!item.isFormField()) {
				zipFile = item;
			} else if(item.isFormField() && "sizeType".equals(item.getFieldName())){
				sizeType = item.getString();
			}
		}
		
		if(null!=zipFile){
			orignalFile = new File(unZipDir + zipFileName);
			try {
				zipFile.write(orignalFile);
				int count = unzipAndCreateImage(zipFileName,unZipDir,loginInfo,sizeType);
				resp.getWriter().print(count);
			} catch (Exception e) {
				//e.printStackTrace();
				throw new IOException(e);
			}
		}
	}

	BufferedOutputStream dest = null;
	BufferedInputStream is = null;
	ZipEntry entry;
	File tempFile = null;
	File realFile = null;
	FileOutputStream fos = null;
	byte data[] = new byte[BUFFER];
	
	
	private String redefineFileName(String name){
		
		String[] names = name.split("\\.");
		
		String prefix = names[0]+"_"+System.nanoTime();
		
		return prefix+"."+names[names.length-1];
	}
	
	private int unzipAndCreateImage(String zipFileName,String unZipDir,LoginInfo loginInfo, String sizeType) throws IOException {
		ZipFile zipfile = new ZipFile(unZipDir + zipFileName);
		Enumeration e = zipfile.entries();
		String fileName = "";
		int counter = 0;
		while (e.hasMoreElements()) {
			try {
				entry = (ZipEntry) e.nextElement();
				is = new BufferedInputStream(zipfile.getInputStream(entry));
				int count;
				fos = new FileOutputStream(unZipDir + "/" + entry.getName());
				dest = new BufferedOutputStream(fos, BUFFER);
				while ((count = is.read(data, 0, BUFFER)) != -1) {
					dest.write(data, 0, count);
				}
				fileName = entry.getName();
				fileName = redefineFileName(fileName.replaceAll("[^a-zA-Z0-9\\.]", "_"));
				tempFile = new File(unZipDir + "/" + entry.getName());
				realFile = new File(getServletContext().getRealPath(
						loginInfo.getSite().getImagePath())
						+ "/" + fileName);
				dest.flush();
				dest.close();
				is.close();
				FileUtils.copyFile(tempFile, realFile);
				tempFile.delete();
				tempFile = null;
				// ProcessImage processImage = new ProcessImage();
				// new Thread(processImage,
				// fileName+"__"+realFile.getAbsolutePath()).start();
			
				processImage(fileName,loginInfo,sizeType);
				counter++;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return counter;
	}
	
	private void processImage(String fileName,LoginInfo loginInfo, String sizeType) {
		ImageService imageService = ServiceFactory
				.getService(ImageService.class);
		Image image = new Image();
		image.setSite(loginInfo.getSite());
		image.setCreateDate(new Date());
		image.setUpdateDate(new Date());
		image.setAltTitle(loginInfo.getSite().getImagePath() + "/" + fileName);
		image.setName(fileName.substring(0,fileName.lastIndexOf('.')));
		image.setNoChangeUrl(loginInfo.getSite().getImagePath() + "/" + fileName);
		image.setStrSizeType(sizeType);
		imageService.saveImage(image, realFile.getAbsolutePath(), loginInfo);
	}

}
