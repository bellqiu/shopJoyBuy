package com.spshop.model;

import java.util.ArrayList;
import java.util.List;

public class TabSelling extends Component{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6082036361869052213L;
	
	private List<TabProduct> tabs = new ArrayList<TabProduct>();
	
	public TabSelling() {
	}

	public TabSelling(TabSelling selling) {
		super(selling);
	}
	
	public void setTabs(List<TabProduct> tabs) {
		this.tabs = tabs;
	}

	public List<TabProduct> getTabs() {
		return tabs;
	}

	public TabSelling clone() {
		TabSelling obj = null;
		obj = new TabSelling(this);
		if (this.tabs != null) {
			/* Does not have a clone() method */
			obj.tabs = new ArrayList<TabProduct>();
			for (TabProduct tab : this.tabs) {
				obj.tabs.add(tab.clone());
			}
		}
		return obj;
	}
	
}
