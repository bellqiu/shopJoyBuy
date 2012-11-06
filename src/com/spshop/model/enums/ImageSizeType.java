package com.spshop.model.enums;

public enum ImageSizeType {
	
	PRODUCT_NORMAL("PRODUCT_NORMAL","Product Normal"),
	PRODUCT_SQUARE("PRODUCT_SQUARE","Product Square"),
	SPECIAL_OFFER("SPECIAL_OFFER","Special Offer"),
	PRODUCT_COLOR("PRODUCT_COLOR","Product Color");
	private String value;
	private String title;
	private ImageSizeType(String value,String title){
		this.value = value;
		this.setTitle(title);
	}
	public String getValue() {
		return value;
	}
	private void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}
}
