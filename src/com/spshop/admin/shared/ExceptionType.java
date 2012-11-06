package com.spshop.admin.shared;

public enum ExceptionType {
	NO_SIGN_IN("NO_SIGN_IN","No sign in");
	
	private String value;
	private String desc;
	private ExceptionType(String value,String desc) {
		this.setValue(value);
		this.setDesc(desc);
	}
	private void setValue(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	private void setDesc(String desc) {
		this.desc = desc;
	}
	public String getDesc() {
		return desc;
	}
}
