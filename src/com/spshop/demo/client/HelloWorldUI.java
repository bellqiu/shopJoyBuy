package com.spshop.demo.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class HelloWorldUI extends Composite{
	interface HelloWorldUIBinder extends UiBinder<Widget, HelloWorldUI>{};
	
	private static HelloWorldUIBinder binder= GWT.create(HelloWorldUIBinder.class);
	
	@UiField ListBox listBox;
	
	public HelloWorldUI() {
		initWidget(binder.createAndBindUi(this));
	}
	
	public void setName(List<String> ns){
		for (String string : ns) {
			listBox.addItem(string);
		}
	}
	
}
