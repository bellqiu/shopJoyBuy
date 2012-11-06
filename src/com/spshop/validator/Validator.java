package com.spshop.validator;

import java.util.Collection;
import java.util.Map;

import com.spshop.exception.ServiceValidateException;

public abstract class Validator<T> {
	private StringBuffer buff = new StringBuffer("<ul>");
	private T component;
	public Validator(T component) {
		this.setComponent(component);
	}
	public void validate(){
		runRules();
		if(!buff.toString().equals("<ul>")){
			throw new ServiceValidateException(buff.append("</ul>").toString());
		}
	}
	
	public abstract void runRules();
	
	public void addMessage(String msg){
		buff.append("<li style='color:red;list-style: decimal;padding: 5px;font-weight: bold;text-shadow: white 2px;'>"+msg+"</li>");
	}
	public void setComponent(T component) {
		this.component = component;
	}
	public T getComponent() {
		return component;
	}
	
	@SuppressWarnings("rawtypes")
	public boolean isEmpty(Object obj){
		if(null==obj){
			return true;
		}
		
		if(obj instanceof CharSequence){
			return obj.toString().matches("\\s*");
		}
		
		if(obj instanceof Collection){
			return ((Collection)obj).isEmpty();
		}
		
		if(obj instanceof Map){
			return ((Map)obj).isEmpty();
		}
		
		return false;
	}
	
	public boolean isNumber(String str){
		if(!isEmpty(str)){
			return str.matches("\\d+(\\.\\d+)*");
		}
		return false;
	}
	
	public boolean isName(String str){
		if(!isEmpty(str)){
			return str.matches("[\\d\\w_-]+");
		}
		return false;
	}


}
