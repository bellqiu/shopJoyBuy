package com.spshop.validator;

import com.spshop.model.Product;

public class ProductValidator extends Validator<Product>{

	public ProductValidator(Product component) {
		super(component);
	}

	@Override
	public void runRules() {
		
		if(isEmpty(getComponent().getTitle())){
			addMessage("Title can not be null!");
		}
		
		if(isEmpty(getComponent().getCategories())){
			addMessage("At least have one category!");
		}
		
		if(isEmpty(getComponent().getImages())){
			addMessage("At least have one image!!");
		}
		
		if(isEmpty(getComponent().getName())){
			addMessage("Name can not be null!");
		}
		
		if(!isName(getComponent().getName())){
			addMessage("Name Only allow a-z, 0-9 , - , _!");
		}
	}

}
