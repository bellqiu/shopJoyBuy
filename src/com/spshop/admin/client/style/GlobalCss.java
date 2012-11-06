package com.spshop.admin.client.style;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.NotStrict;

public interface GlobalCss extends ClientBundle {
	@NotStrict
	@Source("global.css")
	CssResource css();
}
