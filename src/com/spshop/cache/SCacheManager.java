package com.spshop.cache;

import java.io.InputStream;
import java.net.URL;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.Configuration;

public class SCacheManager extends CacheManager{
	
	
	public SCacheManager() throws CacheException {
		super();
		// TODO Auto-generated constructor stub
	}

	public SCacheManager(Configuration configuration) throws CacheException {
		super(configuration);
		// TODO Auto-generated constructor stub
	}

	public SCacheManager(InputStream configurationInputStream)
			throws CacheException {
		super(configurationInputStream);
		// TODO Auto-generated constructor stub
	}

	public SCacheManager(String configurationFileName) throws CacheException {
		super(configurationFileName);
		// TODO Auto-generated constructor stub
	}

	public SCacheManager(URL configurationURL) throws CacheException {
		super(configurationURL);
		// TODO Auto-generated constructor stub
	}

	public SCache getSCache(String key){
		return new SCache(getCache(key));
	}
}
