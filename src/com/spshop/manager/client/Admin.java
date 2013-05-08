package com.spshop.manager.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dev.jjs.impl.GwtAstBuilder;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.spshop.admin.client.businessui.service.AdminServiceAsync;
import com.spshop.manager.client.service.HelloService;
import com.spshop.manager.client.service.HelloServiceAsync;

public class Admin implements EntryPoint {

	@Override
	public void onModuleLoad() {
		final TextBox textBox = new TextBox();
		Button button = new Button("Click");
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		
		horizontalPanel.add(textBox);
		horizontalPanel.add(button);
		
		
		button.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent e) {
				HelloServiceAsync helloServiceAsync = GWT.create(HelloService.class);
				helloServiceAsync.hello(textBox.getValue(), new AsyncCallback<String>() {
					
					@Override
					public void onSuccess(String name) {
						Window.alert(name);
					}
					
					@Override
					public void onFailure(Throwable error) {
						
					}
				});
			}
		});
		
		RootPanel.get().add(horizontalPanel);
	}

}
