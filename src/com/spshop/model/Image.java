package com.spshop.model;

import com.spshop.model.enums.ImageSizeType;
import com.spshop.model.enums.ImageType;

public class Image extends Component{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1698610860522003320L;
	
	private String altTitle;
	private String noChangeUrl;

	private String logoUrl;
	private String iconUrl;
	private String largerUrl;
	private String thumbnailUrl;
	private String smallUrl;
	private String strType;
	private String strSizeType;
	
	public Image() {
	}
	
	public Image(Image image) {
		super(image);
	}
	
	public Image(Image image,boolean withSite) {
		super(image,withSite);
	}
	
	public String getNoChangeUrl() {
		return noChangeUrl;
	}
	
	public String getLargerUrl() {
		return largerUrl;
	}
	public void setLargerUrl(String largerUrl) {
		this.largerUrl = largerUrl;
	}
	public void setNoChangeUrl(String noChangeUrl) {
		this.noChangeUrl = noChangeUrl;
	}
	public String getLogoUrl() {
		return logoUrl;
	}
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
	public String getSmallUrl() {
		return smallUrl;
	}
	public void setSmallUrl(String smallUrl) {
		this.smallUrl = smallUrl;
	}
	public String getStrType() {
		return strType;
	}
	public void setStrType(String strType) {
		this.strType = strType;
	}
	public String getStrSizeType() {
		return strSizeType;
	}
	public void setStrSizeType(String strSizeType) {
		this.strSizeType = strSizeType;
	}
	
	public ImageType getType() {
		if(null==strType){
			return null;
		}
		return ImageType.valueOf(strType.toUpperCase());
	}
	public void setType(ImageType type) {
		this.strType = type.getValue();
	}
	public void setSizeType(ImageSizeType sizeType) {
		this.strSizeType = sizeType.getValue();
	}
	public ImageSizeType getSizeType() {
		if(null==strSizeType){
			return null;
		}
		return ImageSizeType.valueOf(strSizeType.toUpperCase());
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setAltTitle(String altTitle) {
		this.altTitle = altTitle;
	}

	public String getAltTitle() {
		return altTitle;
	}
	
	public Image clone(){
		return clone(true);
	}

	public Image clone(boolean withSite) {
		Image obj = null;
		if(withSite){
			obj = new Image(this);
		}else{
			obj = new Image(this,false);
		}
		if (this.altTitle != null) {
			/* Does not have a clone() method */
			obj.altTitle = this.altTitle;
		}
		if (this.noChangeUrl != null) {
			/* Does not have a clone() method */
			obj.noChangeUrl = this.noChangeUrl;
		}
		if (this.logoUrl != null) {
			/* Does not have a clone() method */
			obj.logoUrl = this.logoUrl;
		}
		if (this.iconUrl != null) {
			/* Does not have a clone() method */
			obj.iconUrl = this.iconUrl;
		}
		if (this.largerUrl != null) {
			/* Does not have a clone() method */
			obj.largerUrl = this.largerUrl;
		}
		if (this.thumbnailUrl != null) {
			/* Does not have a clone() method */
			obj.thumbnailUrl = this.thumbnailUrl;
		}
		if (this.smallUrl != null) {
			/* Does not have a clone() method */
			obj.smallUrl = this.smallUrl;
		}
		if (this.strType != null) {
			/* Does not have a clone() method */
			obj.strType = this.strType;
		}
		if (this.strSizeType != null) {
			/* Does not have a clone() method */
			obj.strSizeType = this.strSizeType;
		}
		return obj;
	}
}
