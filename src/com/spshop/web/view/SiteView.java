package com.spshop.web.view;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.spshop.model.Category;
import com.spshop.model.Country;
import com.spshop.model.Site;

public class SiteView {
	private Site site;
	private List<Category> categories;
	private String host;
	private String imageHost;
	private String currency;
	private Map<String,Float> currencies;
	private List<Country> countries = new ArrayList<Country>();
	private Map<String,Country> countryMap = new HashMap<String,Country>();
	
	public Site getSite() {
		return site;
	}
	public void setSite(Site site) {
		this.site = site;
	}
	public List<Category> getCategories() {
		return categories;
	}
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getImageHost() {
		return imageHost;
	}
	public void setImageHost(String imageHost) {
		this.imageHost = imageHost;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Map<String,Float> getCurrencies() {
		return currencies;
	}
	public void setCurrencies(Map<String,Float> currencies) {
		this.currencies = currencies;
	}
	public List<Country> getCountries() {
		return countries;
	}
	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}
	public Map<String,Country> getCountryMap() {
		return countryMap;
	}
	public void setCountryMap(Map<String,Country> countryMap) {
		this.countryMap = countryMap;
	}
	
}
