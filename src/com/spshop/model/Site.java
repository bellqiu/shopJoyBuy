package com.spshop.model;

public class Site extends Component{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8581772837790501820L;
	
	private Image logo;
	private String prefix;
	private String suffix;
	private String domain;
	private String imagePath;
	private String imageMagicCommand;
	private String siteFooter;
	private String sideBar;
	private Image featuredCat;
	private Image delivery;
	private float freeDeliveryPrice;
	private String imgDomain;
	
	public Image getFeaturedCat() {
		return featuredCat;
	}

	public void setFeaturedCat(Image featuredCat) {
		this.featuredCat = featuredCat;
	}

	public Image getDelivery() {
		return delivery;
	}

	public void setDelivery(Image delivery) {
		this.delivery = delivery;
	}

	public String getFeaturedCatURL() {
		return featuredCatURL;
	}

	public void setFeaturedCatURL(String featuredCatURL) {
		this.featuredCatURL = featuredCatURL;
	}

	public String getDeliveryURL() {
		return deliveryURL;
	}

	public void setDeliveryURL(String deliveryURL) {
		this.deliveryURL = deliveryURL;
	}

	private String featuredCatURL;
	private String deliveryURL;
	
	public Site() {
	}
	
	public Site(Site site) {
		super(site);
	}

	public Image getLogo() {
		return logo;
	}

	public void setLogo(Image logo) {
		this.logo = logo;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public void setImageMagicCommand(String imageMagicCommand) {
		this.imageMagicCommand = imageMagicCommand;
	}

	public String getImageMagicCommand() {
		return imageMagicCommand;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getDomain() {
		return domain;
	}
	
	public String getHost(){
		return prefix+"."+domain+"."+suffix;
	}

	public void setSiteFooter(String siteFooter) {
		this.siteFooter = siteFooter;
	}

	public String getSiteFooter() {
		return siteFooter;
	}

	public void setSideBar(String sideBar) {
		this.sideBar = sideBar;
	}

	public String getSideBar() {
		return sideBar;
	}

	public Site clone() {
		Site obj = null;
		obj = new Site(this);
		if (this.logo != null) {
			obj.logo = (Image) this.logo.clone(false);
			obj.logo.setSite(obj);
		}
		if (this.prefix != null) {
			/* Does not have a clone() method */
			obj.prefix = this.prefix;
		}
		if (this.suffix != null) {
			/* Does not have a clone() method */
			obj.suffix = this.suffix;
		}
		if (this.domain != null) {
			/* Does not have a clone() method */
			obj.domain = this.domain;
		}
		if (this.imagePath != null) {
			/* Does not have a clone() method */
			obj.imagePath = this.imagePath;
		}
		if (this.imageMagicCommand != null) {
			/* Does not have a clone() method */
			obj.imageMagicCommand = this.imageMagicCommand;
		}
		if (this.siteFooter != null) {
			/* Does not have a clone() method */
			obj.siteFooter = this.siteFooter;
		}
		if (this.sideBar != null) {
			/* Does not have a clone() method */
			obj.sideBar = this.sideBar;
		}
		if (this.featuredCat != null) {
			obj.featuredCat = (Image) this.featuredCat.clone(false);
			obj.featuredCat.setSite(obj);
		}
		if (this.delivery != null) {
			obj.delivery = (Image) this.delivery.clone(false);
			obj.delivery.setSite(obj);
		}
		if (this.featuredCatURL != null) {
			/* Does not have a clone() method */
			obj.featuredCatURL = this.featuredCatURL;
		}
		if (this.deliveryURL != null) {
			/* Does not have a clone() method */
			obj.deliveryURL = this.deliveryURL;
		}
		
		if (this.imgDomain != null) {
			/* Does not have a clone() method */
			obj.imgDomain = this.imgDomain;
		}
		
		obj.freeDeliveryPrice = this.freeDeliveryPrice;
		
		return obj;
	}

	public void setFreeDeliveryPrice(float freeDeliveryPrice) {
		this.freeDeliveryPrice = freeDeliveryPrice;
	}

	public float getFreeDeliveryPrice() {
		return freeDeliveryPrice;
	}

	public String getImgDomain() {
		return imgDomain;
	}

	public void setImgDomain(String imgDomain) {
		this.imgDomain = imgDomain;
	}

}
