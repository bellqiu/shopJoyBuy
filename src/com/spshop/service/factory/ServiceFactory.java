package com.spshop.service.factory;

import com.spshop.service.ServiceLocator;

public class ServiceFactory{
	@SuppressWarnings("unchecked")
	public  static<T> T getService(Class<T> t){
		return (T) ServiceLocator.getBean(t.getName());
	}
}
