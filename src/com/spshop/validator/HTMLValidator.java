package com.spshop.validator;

import com.spshop.model.HTML;

public class HTMLValidator extends Validator<HTML>{

	public HTMLValidator(HTML component) {
		super(component);
	}

	@Override
	public void runRules() {
		if(isEmpty(getComponent().getName())){
			addMessage("HTML name cannot be null !");
		}
	}

}
