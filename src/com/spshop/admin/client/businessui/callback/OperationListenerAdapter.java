package com.spshop.admin.client.businessui.callback;

import com.google.gwt.user.client.Window;
import com.spshop.model.Component;
public class OperationListenerAdapter<T extends Component> implements OperationListener<T>{

	@Override
	public void onEdit(T content) {
		Window.alert("Not implement");
	}

	@Override
	public void onDelete(T content) {
		Window.alert("Not implement");
	}

}
