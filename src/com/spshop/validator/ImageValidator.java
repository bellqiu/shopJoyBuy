package com.spshop.validator;

import com.spshop.model.Image;

public class ImageValidator extends Validator<Image>{

	public ImageValidator(Image component) {
		super(component);
	}

	@Override
	public void runRules() {
		if(isEmpty(getComponent().getName())){
			addMessage("Image name cannot be null !");
		}
	}

}
