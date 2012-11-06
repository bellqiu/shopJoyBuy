package com.spshop.validator;

import com.spshop.model.Category;

public class CategoryValidator extends Validator<Category>{

	public CategoryValidator(Category component) {
		super(component);
	}

	@Override
	public void runRules() {
		if(isEmpty(getComponent().getName())){
			addMessage("Category name cannot be null !");
		}
		if(isEmpty(getComponent().getDisplayName())){
			addMessage("Category diplay name cannot be null !");
		}
		if(!isName(getComponent().getName())){
			addMessage("Name Only allow a-z, 0-9 , - , _!");
		}
	}

}
