package com.spshop.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShopMenu implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8572669581171195938L;
	private String title;
	private String action;
	private List<ShopMenu> subList = new ArrayList<ShopMenu>();
	
	public ShopMenu() {
	}
	

	public ShopMenu(String title, String action) {
		super();
		this.title = title;
		this.action = action;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public List<ShopMenu> getSubList() {
		return subList;
	}

	public void setSubList(List<ShopMenu> subList) {
		this.subList = subList;
	}
	
	
}
 