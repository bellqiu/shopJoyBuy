package com.spshop.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.imageio.ImageIO;

import magick.MagickException;

import org.apache.log4j.Logger;

import com.spshop.admin.shared.ImageSize;
import com.spshop.admin.shared.LoginInfo;
import com.spshop.model.Image;
import com.spshop.model.enums.ImageSizeType;

public class ImageTools {
	
	private static Logger logger = Logger.getLogger(ImageTools.class);
	
	private static String  COVERTPAHT="C:\\Program Files (x86)\\ImageMagick-5.5.7-Q8";
	private final static String COMMAND_KEY = "command.path";
	
	private static Properties IMAGE_SIZES = new Properties();
	
	static{
		Properties pro = new Properties();
		try {
			
			pro.load(ImageTools.class.getResourceAsStream("/imagemagick.properties"));
			IMAGE_SIZES.load(ImageTools.class.getResourceAsStream("/imagemagick.properties"));
			
			COVERTPAHT = pro.getProperty(COMMAND_KEY);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static int[] getXY(ImageSizeType type, ImageSize size){
		
		int[] rSize = new int[2];
		
		String key = "image.size.";
		key = key + type.getValue()+"."+size;
		String value = IMAGE_SIZES.getProperty(key);
		if(null == value){
			key = "image.size.DEFAULT."+size;
			value = IMAGE_SIZES.getProperty(key);
		}
		
		String[] sizes = value.split(",");
		
		rSize[0] = Integer.valueOf(sizes[0]);
		rSize[1] = Integer.valueOf(sizes[1]);
		
		return rSize;
	}
	

	public static Image changeSize(Image img, LoginInfo loginInfo,
			String filePath) throws MagickException, IOException {
		
			String [] names = reSizeWithBorder(filePath, img.getSizeType());
			
			img.setLargerUrl(loginInfo.getSite().getImagePath() + "/"+ names[0]);
			
			img.setLogoUrl((loginInfo.getSite().getImagePath() + "/" + names[1]));
			
			img.setThumbnailUrl((loginInfo.getSite().getImagePath() + "/" + names[2]));
			
			img.setSmallUrl((loginInfo.getSite().getImagePath() + "/" + names[3]));
			
			img.setIconUrl(loginInfo.getSite().getImagePath()+ "/" + names[4]);

			return img;
	}
	
	public static String[] reSizeWithBorder(String filePath, ImageSizeType sizeType)
	throws MagickException, IOException {
		String[] imageNames = new String[ImageSize.values().length];
		String toPath = getImagePath(filePath);
		
		String largePathRelative = getImageName(filePath, getXY(sizeType,ImageSize.LARGE_SIZE)[0], getXY(sizeType,ImageSize.LARGE_SIZE)[1]);
		String largePath = toPath + "/"+ largePathRelative;
		
		String logoPathRelative = getImageName(filePath, getXY(sizeType,ImageSize.LOGO_SIZE)[0], getXY(sizeType,ImageSize.LOGO_SIZE)[1]);
		String logoPath = toPath + "/"+ logoPathRelative;
		
		String thumPathRelative = getImageName(filePath, getXY(sizeType,ImageSize.THUM_SIZE)[0], getXY(sizeType,ImageSize.THUM_SIZE)[1]);
		String thumPath = toPath + "/"+ thumPathRelative;
		
		String smallPathRelative = getImageName(filePath, getXY(sizeType,ImageSize.SMALL_SIZE)[0], getXY(sizeType,ImageSize.SMALL_SIZE)[1]);
		String smallPath = toPath + "/"+ smallPathRelative;
		
		String iconPathRelative = getImageName(filePath, getXY(sizeType,ImageSize.ICON_SIZE)[0], getXY(sizeType,ImageSize.ICON_SIZE)[1]);
		String iconPath = toPath + "/"+ iconPathRelative;
		
		String cmd1 = COVERTPAHT+" " + filePath + " -resize " + getXY(sizeType,ImageSize.LARGE_SIZE)[0] +"x"
		+ getXY(sizeType,ImageSize.LARGE_SIZE)[1]+ " " + largePath;
		
		String cmd2 = COVERTPAHT+" " + filePath + " -resize " + getXY(sizeType,ImageSize.LOGO_SIZE)[0] +"x"
		+ getXY(sizeType,ImageSize.LOGO_SIZE)[1]+ " " + logoPath;
		
		String cmd3 = COVERTPAHT+" " + filePath + " -resize " + getXY(sizeType,ImageSize.THUM_SIZE)[0] +"x"
		+ getXY(sizeType,ImageSize.THUM_SIZE)[1]+ " " + thumPath;
		
		String cmd4 = COVERTPAHT+" " + filePath + " -resize " + getXY(sizeType,ImageSize.SMALL_SIZE)[0] +"x"
		+ getXY(sizeType,ImageSize.SMALL_SIZE)[1]+ " " + smallPath;
		
		String cmd5 = COVERTPAHT+" " + filePath + " -resize " + getXY(sizeType,ImageSize.ICON_SIZE)[0] +"x"
		+ getXY(sizeType,ImageSize.ICON_SIZE)[1]+ " " + iconPath;
		
		Runtime runtime = Runtime.getRuntime();
		
		Process process = runtime.exec(cmd1);
		Process process1 = runtime.exec(cmd2);
		Process process2 = runtime.exec(cmd3);
		Process process3 = runtime.exec(cmd4);
		Process process4 = runtime.exec(cmd5);
		
	     while (true) {  
	            try {  
	            	process.waitFor();  
	            	process1.waitFor(); 
	            	process2.waitFor(); 
	            	process3.waitFor(); 
	            	process4.waitFor(); 
	                break;
	            } catch (java.lang.InterruptedException e) {  
	            	logger.error(e);
	            }  
	      }  
		
	     borderImage(largePath, getXY(sizeType,ImageSize.LARGE_SIZE)[1], getXY(sizeType,ImageSize.LARGE_SIZE)[0]);
	     borderImage(logoPath, getXY(sizeType,ImageSize.LOGO_SIZE)[1], getXY(sizeType,ImageSize.LOGO_SIZE)[0]);
	     borderImage(thumPath, getXY(sizeType,ImageSize.THUM_SIZE)[1], getXY(sizeType,ImageSize.THUM_SIZE)[0]);
	     borderImage(smallPath, getXY(sizeType,ImageSize.SMALL_SIZE)[1], getXY(sizeType,ImageSize.SMALL_SIZE)[0]);
	     borderImage(iconPath, getXY(sizeType,ImageSize.ICON_SIZE)[1], getXY(sizeType,ImageSize.ICON_SIZE)[0]);
		
	     imageNames[0] = largePathRelative;
	     imageNames[1] = logoPathRelative;
	     imageNames[2] = thumPathRelative;
	     imageNames[3] = smallPathRelative;
	     imageNames[4] = iconPathRelative;
	     
		return imageNames;
	}
	
	private static void borderImage(String filePath, int height, int width){
		 try {  
	            File f = new File(filePath);  
	            BufferedImage bi = ImageIO.read(f); 
	            
	            int srcH = bi.getHeight();
	    		int srcW = bi.getWidth();
	    		
	    		int y = height - srcH;
	    		int x = width - srcW;
	    		
	    		if(x>0){
	    			x = x/2;
	    		}
	    		
	    		if(y>0){
	    			y = y/2;
	    		}
	    		
	    		BufferedImage targetImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    		
	    		Graphics g = targetImage.getGraphics();
	    		Color  c = g.getColor();
	    		g.setColor(Color.white);
	    		g.fillRect(0, 0, width, height);
	    		g.setColor(c);
	    		g.drawImage(bi, x, y, null);
	    		
	            ImageIO.write((BufferedImage) targetImage, "jpg", f);  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	}

	@SuppressWarnings("unused")
	private static String[] reSize(int[][] size, String filePath)
			throws MagickException, IOException {
		
		String[] imageNames = new String[size.length];
		int height = size[0][1];
		int width = size[0][0];
		
		String toPath = getImagePath(filePath);

		java.io.File file = new java.io.File(filePath);

		java.awt.image.BufferedImage bi = javax.imageio.ImageIO.read(file);

		int srcH = bi.getHeight();
		int srcW = bi.getWidth();

		int fullH = srcH;
		int fullW = srcW;

		if (srcH * srcH / srcW > srcH * height / width) {
			fullH = srcW * height / width;
		} else {
			fullW = srcH * width / height;
		}
		
		Runtime runtime = Runtime.getRuntime();
		
		String tempPath = toPath + "/"+ getImageName(filePath, fullH, fullW);
		try {
			String [] cmds = new String[size.length];
			String initCMD=COVERTPAHT+" " + filePath + " -crop " + fullW+ "x"
			+ fullH + "+0+0 " + tempPath;
			for(int i = 0; i<cmds.length ; i++){
				String toImage = getImageName(filePath, size[i][0], size[i][1]);
				cmds[i] =COVERTPAHT+" " + tempPath + " -resize " +size[i][0] +"x"
				+ size[i][1]+ " " + toPath + "/"+  toImage;
				imageNames[i] = toImage;
			}
			Process process = runtime.exec(initCMD);
			InputStream in = process.getErrorStream();
			int r = 0;
			
			while((r=in.read())!=-1){
				System.out.print((char)r);
			}
			in.close();
			runtime = Runtime.getRuntime();
			for (String cmd : cmds) {
				runtime.exec(cmd);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imageNames;
	}
	
	private static String getImageName(String imagePath, int hight, int width) {

		String[] images = imagePath.split("[\\\\|/]");
		String name = images[images.length - 1];
		String[] names = name.split("\\.");
		if (hight < 1 || width < 1) {
			return name;
		}
		return names[0] + "_" + width + "X" + hight + "."
				+ names[names.length - 1];
	}

	public static String getImagePath(String imagePath) {
		File file = new File(imagePath).getParentFile();
		return file.getAbsolutePath();
	}
	
	public static void main(String[] args) throws MagickException, IOException {
		reSizeWithBorder("C:\\T\\Tulips.jpg", ImageSizeType.PRODUCT_SQUARE);
	}


}
