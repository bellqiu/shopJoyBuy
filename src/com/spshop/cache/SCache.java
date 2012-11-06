package com.spshop.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;


public class SCache {
	private Cache cache;
	public SCache(Cache cache) {
		this.setCache(cache);
	}
	
	public Object get(String key){
		if(null!=cache.get(key)){
			return cache.get(key).getValue();
		}
		return null;
	}
	
	public void put(String key, Object value){
		cache.put(new Element(key,value));
	}
	
	public void setCache(Cache cache) {
		this.cache = cache;
	}
	public Cache getCache() {
		return cache;
	}
}
