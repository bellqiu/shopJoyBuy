package com.spshop.admin.client.businessui.callback;

import com.google.gwt.user.client.ui.Widget;

public interface ChangeObservable <C,W extends Widget>{
	void addChangeListener(EditorChangeListener<C,W> listener);
	void notifyChange();
	void notifyDelete();
}
