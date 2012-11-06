package com.spshop.model.query;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.spshop.model.Site;


public class QueryCriteria implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5039324373818202449L;
	private String key;
	private Date start;
	private Date end;
	private String type;//Image,Product,
	private int maxResult = 30;//
	private int startIndex;//5
	private String orderBy;
	private boolean asc=false;
	private Site site;
	private Map<String,Object> properties = new HashMap<String,Object>();
	

	public QueryCriteria() {
	}
	
	

	public QueryCriteria(int maxResult) {
		super();
		this.maxResult = maxResult;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}


	public int getMaxResult() {
		return maxResult;
	}

	public void setMaxResult(int maxResult) {
		this.maxResult = maxResult;
	}


	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public Site getSite() {
		return site;
	}

	public void addProperty(String name,Object value) {
		properties.put(name, value);
	}

	public Map<String,Object> getProperties() {
		return properties;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setAsc(boolean asc) {
		this.asc = asc;
	}

	public boolean isAsc() {
		return asc;
	}
	
}
