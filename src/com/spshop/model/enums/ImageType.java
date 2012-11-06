package com.spshop.model.enums;

public enum ImageType {
	INTERNAL("INTERNAL"),
	EXTERNAL("EXTERNAL");
	private String value;
	private ImageType(String value){
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}
}
