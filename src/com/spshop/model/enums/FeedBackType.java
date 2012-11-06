package com.spshop.model.enums;

public enum FeedBackType {
	COMPLAIN("COMPLAIN");
	private String value;
	private FeedBackType(String value){
		this.value = value;
	}
	public String getValue(){
		return value;
	}
	public static FeedBackType getType(String name) {
    	for (FeedBackType type : FeedBackType.values()) {
    		if (type.getValue().equalsIgnoreCase(name)) {
    			return type;
    		}
    	}
    	return null;
    }
}
