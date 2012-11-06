package com.spshop.admin.client.event;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.shared.GwtEvent;

public class SearchEvent extends GwtEvent<SearchEventHandler>{
	public static Type<SearchEventHandler> TYPE = new Type<SearchEventHandler>();
	
	private String type;
	private long id;
	
	private String name;
	
	private Map<String, Object> criteria = new HashMap<String, Object>();
	
	@Override
	protected void dispatch(SearchEventHandler handler) {
		handler.onSearch(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<SearchEventHandler> getAssociatedType() {
		return TYPE;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Object> getCriteria() {
		return criteria;
	}

	public void setCriteria(Map<String, Object> criteria) {
		this.criteria = criteria;
	}

}
