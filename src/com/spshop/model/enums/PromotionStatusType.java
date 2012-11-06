package com.spshop.model.enums;

public enum PromotionStatusType {
	DISABLED("DISABLED"),
	ENABLED("ENABLED");
	
	private String value;
	private PromotionStatusType( String value){
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}
}
