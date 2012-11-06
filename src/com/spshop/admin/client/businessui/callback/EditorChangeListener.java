package com.spshop.admin.client.businessui.callback;

import com.google.gwt.user.client.ui.Widget;

public interface EditorChangeListener<C,W extends Widget> {
	void onChange(C component, W widget);
	void onDelete(C component, W widget);
}
