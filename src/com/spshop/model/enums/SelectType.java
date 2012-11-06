package com.spshop.model.enums;

public enum SelectType {
	INPUT_TEXT("INPUT_TEXT","Input Text"),
	SINGLE_LIST("SINGLE_LIST","Single List"),
	COLOR_SINGLE("COLOR_SINGLE","Single Color"),
	MULTI_LIST("MULTI_LIST","Multi List");
	private String value;
	private String title;
	private SelectType(String value,String title){
		this.value = value;
		this.title = title;
	}
	
	public String getValue(){
		return value;
	}

	public String getTitle() {
		return title;
	}
}
