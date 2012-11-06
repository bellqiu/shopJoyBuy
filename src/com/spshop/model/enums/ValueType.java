package com.spshop.model.enums;

public enum ValueType {
	INT("INT"),
	FLOAT("FLOAT"),
	STRING("STRING"),
	DATE("DATE");
	
	private String value;
	
	private ValueType(String value){
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}
}
