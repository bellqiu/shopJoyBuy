package com.spshop.admin.client.businessui;

import com.google.gwt.user.client.ui.TreeItem;
import com.spshop.model.Category;

public class CategoryTreeItem extends TreeItem{
	private Category category;
	
	public CategoryTreeItem(Category category) {
		setCategory(category);
	}

	public void setCategory(Category category) {
		this.category = category;
		this.removeItems();
		setText(category.getDisplayName());
		if(null!=category.getSubCategories()&&!category.getSubCategories().isEmpty()){
			for(Category c : category.getSubCategories()){
				addItem(new CategoryTreeItem(c));
			}
		}
	}

	public Category getCategory() {
		return category;
	}

}
