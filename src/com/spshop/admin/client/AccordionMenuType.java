package com.spshop.admin.client;

public enum AccordionMenuType {
	
	RESOURCE_MANAGEMENT("RESOURCE_MANAGEMENT"),
	PRODUCT_MANAGEMENT("PRODUCT_MANAGEMENT"),
	SITE_MANAGEMENT("SITE_MANAGEMENT"),
	USER_MANAGEMENT("USER_MANAGEMENT");
	private String value;
	private AccordionMenuType(String value) {
		this.setValue(value);
	}
	private void setValue(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}

}
