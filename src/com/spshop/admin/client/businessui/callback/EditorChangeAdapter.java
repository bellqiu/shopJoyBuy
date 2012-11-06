package com.spshop.admin.client.businessui.callback;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class EditorChangeAdapter<C,W extends Widget> implements EditorChangeListener<C,W>{

	@Override
	public void onChange(C component, W widget) {
		Window.alert("No implement yet!");
	}

	@Override
	public void onDelete(C component, W widget) {
		Window.alert("No implement yet!");
	}

}
