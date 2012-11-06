package com.spshop.admin.client.businessui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.spshop.admin.client.businessui.callback.OperationListener;
import com.spshop.model.Component;
@SuppressWarnings("rawtypes")
public class Operation<T extends Component> extends Composite {

	private static OperationUiBinder uiBinder = GWT
			.create(OperationUiBinder.class);
	@UiField Button edit;
	@UiField Button delete;
	
	private T item;
	
	private OperationListener<T> listener;

	interface OperationUiBinder extends UiBinder<Widget, Operation> {
	}

	public Operation(T item) {
		initWidget(uiBinder.createAndBindUi(this));
		this.setItem(item);
	}

	@UiHandler("edit")
	void onEditClick(ClickEvent event) {
		if(null!=getListener()){
			getListener().onEdit(item);
		}
	}
	@UiHandler("delete")
	void onDelateClick(ClickEvent event) {
	}

	public void setListener(OperationListener<T> listener) {
		this.listener = listener;
	}

	public OperationListener<T> getListener() {
		return listener;
	}

	public void setItem(T item) {
		this.item = item;
	}

	public T getItem() {
		return item;
	}
}
